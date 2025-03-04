package org.tutorBridge.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tutorBridge.dto.NewReservationDTO;
import org.tutorBridge.dto.StatusChangeDTO;
import org.tutorBridge.entities.*;
import org.tutorBridge.entities.enums.ReservationStatus;
import org.tutorBridge.repositories.AvailabilityRepo;
import org.tutorBridge.repositories.ReservationRepo;
import org.tutorBridge.repositories.StudentRepo;
import org.tutorBridge.repositories.TutorRepo;
import org.tutorBridge.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;
    private final TutorRepo tutorRepo;
    private final StudentRepo studentRepo;
    private final AvailabilityRepo availabilityRepo;

    public ReservationService(ReservationRepo reservationRepo, TutorRepo tutorRepo, StudentRepo studentRepo, AvailabilityRepo availabilityRepo) {
        this.reservationRepo = reservationRepo;
        this.tutorRepo = tutorRepo;
        this.studentRepo = studentRepo;
        this.availabilityRepo = availabilityRepo;
    }

    @Transactional
    public void makeReservations(Student student, List<NewReservationDTO> reservationsData) {
        for (NewReservationDTO reservationData : reservationsData) {
            makeReservation(student, reservationData);
        }
        studentRepo.update(student);
    }

    private void makeReservation(Student student, NewReservationDTO data) {
        Availability slot = availabilityRepo.findWithTutorAndSpecializations(data.getAvailabilityId())
                .orElseThrow(() -> new ValidationException("Availability not found"));


        Tutor tutor = slot.getTutor();
        Specialization specialization = tutor.getSpecializations().stream()
                .filter(s -> s.getName().equalsIgnoreCase(data.getSpecializationName()))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Specialization not found"));

        Reservation reservation = new Reservation(
                student,
                tutor,
                specialization,
                slot.getStartDateTime(),
                slot.getEndDateTime()
        );

        student.addReservation(reservation);
        tutor.addReservation(reservation);

        studentRepo.save(student);
        tutorRepo.update(tutor);
        reservationRepo.save(reservation);

        availabilityRepo.deleteAllOverlapping(tutor, slot.getStartDateTime(), slot.getEndDateTime());
    }


    @Transactional
    public void changeReservationStatus(Tutor tutor, List<StatusChangeDTO> statusChanges) {
        List<Long> reservationIds = statusChanges.stream().map(StatusChangeDTO::getReservationId).toList();
        List<Reservation> reservations = reservationRepo.findReservationsByTutorAndIds(tutor, reservationIds);
        if (reservations.size() != reservationIds.size()) {
            throw new ValidationException("Some reservations do not belong to the tutor");
        }

        IntStream.range(0, statusChanges.size()).forEach(i -> {
            Reservation reservation = reservations.get(i);
            StatusChangeDTO statusChange = statusChanges.get(i);
            if (reservation.getStatus() == ReservationStatus.CANCELLED
                    && statusChange.getStatus() != ReservationStatus.CANCELLED) {
                throw new ValidationException("Cannot change status of a reservation that is already cancelled");
            }
            if (reservation.getStatus() == ReservationStatus.ACCEPTED
                    && statusChange.getStatus() == ReservationStatus.NEW) {
                throw new ValidationException("Cannot change status of a reservation that is already accepted to new");
            }

            reservation.setStatus(statusChange.getStatus());
            reservationRepo.update(reservation);
        });
    }


    @Transactional
    public void cancelReservation(Student student, Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new ValidationException("Reservation not found"));

        if (student != reservation.getStudent())
            throw new ValidationException("Reservation does not belong to the student");

        if (reservation.getStartDateTime().isBefore(LocalDateTime.now()))
            throw new ValidationException("Cannot cancel reservation in the past");

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepo.update(reservation);

        Availability availability = new Availability(
                reservation.getTutor(),
                reservation.getStartDateTime(),
                reservation.getEndDateTime()
        );
        availabilityRepo.insertIfNoConflicts(availability);
    }

}
