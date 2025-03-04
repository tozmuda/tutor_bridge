package org.tutorBridge.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tutorBridge.dto.AbsenceDTO;
import org.tutorBridge.dto.TimeFrameDTO;
import org.tutorBridge.entities.Absence;
import org.tutorBridge.entities.Tutor;
import org.tutorBridge.repositories.AbsenceRepo;
import org.tutorBridge.repositories.AvailabilityRepo;
import org.tutorBridge.repositories.ReservationRepo;
import org.tutorBridge.repositories.TutorRepo;
import org.tutorBridge.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbsenceService {
    private final AbsenceRepo absenceRepo;
    private final AvailabilityRepo availabilityRepo;
    private final ReservationRepo reservationRepo;

    public AbsenceService(AbsenceRepo absenceRepo, AvailabilityRepo availabilityRepo, ReservationRepo reservationRepo, TutorRepo tutorRepo) {
        this.absenceRepo = absenceRepo;
        this.availabilityRepo = availabilityRepo;
        this.reservationRepo = reservationRepo;
    }

    @Transactional
    public List<AbsenceDTO> addAbsence(Tutor tutor, LocalDateTime start, LocalDateTime end) {
        if (absenceRepo.overlappingAbsenceExists(tutor, start, end)) {
            throw new ValidationException("Tutor already has an absence that conflicts with the new one.");
        }

        Absence absence = new Absence(tutor, start, end);
        absenceRepo.save(absence);
        availabilityRepo.deleteAllOverlapping(tutor, start, end);
        reservationRepo.cancelAllOverlapping(tutor, start, end);

        return fromAbsencesToDTOS(absenceRepo.fetchAbsences(tutor));
    }

    @Transactional(readOnly = true)
    public List<AbsenceDTO> getAbsences(Tutor tutor, TimeFrameDTO timeFrame) {
        TimeFrameDTO.fillInEmptyFields(timeFrame);
        return fromAbsencesToDTOS(absenceRepo.fetchAbsences(tutor, timeFrame.getStart(), timeFrame.getEnd()));
    }

    @Transactional
    public List<AbsenceDTO> deleteAbsence(Tutor tutor, Long absenceId) {
        Absence absence = absenceRepo.findById(absenceId)
                .orElseThrow(() -> new ValidationException("Absence not found"));
        if (!absence.getTutor().equals(tutor)) {
            throw new ValidationException("Absence does not belong to tutor");
        }

        absenceRepo.delete(absence);

        return fromAbsencesToDTOS(absenceRepo.fetchAbsences(tutor));
    }


    private List<AbsenceDTO> fromAbsencesToDTOS(Collection<Absence> absences) {
        return absences.stream()
                .map(a -> new AbsenceDTO(a.getAbsenceId(), a.getStartDate(), a.getEndDate()))
                .collect(Collectors.toList());
    }

}
