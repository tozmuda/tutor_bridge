package org.tutorBridge.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tutorBridge.dto.*;
import org.tutorBridge.entities.Availability;
import org.tutorBridge.entities.Tutor;
import org.tutorBridge.repositories.AbsenceRepo;
import org.tutorBridge.repositories.AvailabilityRepo;
import org.tutorBridge.repositories.SpecializationRepo;
import org.tutorBridge.repositories.TutorRepo;
import org.tutorBridge.validation.ValidationException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    private final AvailabilityRepo availabilityRepo;
    private final TutorRepo tutorRepo;
    private final AbsenceRepo absenceRepo;
    private final SpecializationRepo specializationRepo;


    public AvailabilityService(AvailabilityRepo availabilityRepo,
                               TutorRepo tutorRepo,
                               AbsenceRepo absenceRepo,
                                SpecializationRepo specializationRepo
    ) {
        this.availabilityRepo = availabilityRepo;
        this.tutorRepo = tutorRepo;
        this.absenceRepo = absenceRepo;
        this.specializationRepo = specializationRepo;
    }

    @Transactional(readOnly = true)
    public List<TutorSearchResultDTO> searchAvailableTutors(TutorSearchRequestDTO request) {
        if(specializationRepo.findByName(request.getSpecializationName()).isEmpty()){
            throw new ValidationException("Specialization does not exist");
        }
        if(request.getStartDateTime().isAfter(request.getEndDateTime())){
            throw new ValidationException("Start date must be before end date");
        }


        List<Tutor> tutors = tutorRepo.findTutorsWithAvailabilities(request.getSpecializationName(), request.getStartDateTime(), request.getEndDateTime());

        return tutors.stream()
                .map(tutor -> {
                    List<AvailabilityDTO> availabilities = fromAvailabilitiesToDTOS(tutor.getAvailabilities());
                    return new TutorSearchResultDTO(
                            tutor.getUserId(),
                            tutor.getFirstName(),
                            tutor.getLastName(),
                            tutor.getPhone(),
                            tutor.getEmail(),
                            tutor.getBio(),
                            availabilities
                    );
                })
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<AvailabilityDTO> getAvailabilities(Tutor tutor, TimeFrameDTO timeFrame) {
        return fromAvailabilitiesToDTOS(
                availabilityRepo.fetchOverlapping(tutor, timeFrame.getStart(), timeFrame.getEnd())
        );
    }

    @Transactional
    public List<AvailabilityDTO> addWeeklyAvailability(Tutor tutor, WeeklySlotsDTO slots) {
        LocalDate startDate = slots.getStartDate();
        LocalDate endDate = slots.getEndDate();
        Map<DayOfWeek, List<TimeRangeDTO>> weeklyTimeRanges = slots.getWeeklyTimeRanges();

        clearTimeFrameFromPreviousAvailabilities(tutor, startDate, endDate);
        clearTimeFrameFromAbsences(tutor, startDate, endDate);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            Set<TimeRangeDTO> timeRanges = Set.copyOf(weeklyTimeRanges.getOrDefault(dayOfWeek, List.of()));
            for (var timeRange : timeRanges) {
                createNewAvailabilityFromTimeRange(tutor, timeRange, date);
            }
        }

        return fromAvailabilitiesToDTOS(availabilityRepo.fetchOverlapping(
                tutor,
                slots.getStartDate().atStartOfDay(),
                slots.getEndDate().plusDays(1).atStartOfDay()));
    }

    private void clearTimeFrameFromPreviousAvailabilities(Tutor tutor, LocalDate startDate, LocalDate endDate) {
        availabilityRepo.deleteAllOverlapping(
                tutor,
                startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay());
    }

    private void clearTimeFrameFromAbsences(Tutor tutor, LocalDate startDate, LocalDate endDate) {
        absenceRepo.deleteOverlapping(
                tutor,
                startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay());
    }

    private void createNewAvailabilityFromTimeRange(Tutor tutor, TimeRangeDTO timeRange, LocalDate date) {
        if (timeRange.getStart() != null && timeRange.getEnd() != null) {
            LocalDateTime startDateTime = LocalDateTime.of(date, timeRange.getStart());
            LocalDateTime endDateTime = LocalDateTime.of(date, timeRange.getEnd());
            Availability availability = new Availability(tutor, startDateTime, endDateTime);
            availabilityRepo.save(availability);
        }
    }

    private List<AvailabilityDTO> fromAvailabilitiesToDTOS(Collection<Availability> availabilities) {
        return availabilities.stream()
                .map(a -> new AvailabilityDTO(a.getAvailabilityId(), a.getStartDateTime(), a.getEndDateTime()))
                .collect(Collectors.toList());
    }


}
