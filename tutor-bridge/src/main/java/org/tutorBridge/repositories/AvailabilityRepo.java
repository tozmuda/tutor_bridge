package org.tutorBridge.repositories;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.tutorBridge.entities.Availability;
import org.tutorBridge.entities.Tutor;
import org.tutorBridge.entities.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AvailabilityRepo extends GenericRepo<Availability, Long> {
    public AvailabilityRepo() {
        super(Availability.class);
    }

    public Optional<Availability> findWithTutorAndSpecializations(Long availabilityId) {
        return em.createQuery("SELECT a FROM Availability a" +
                                " JOIN FETCH a.tutor t" +
                                " JOIN FETCH t.specializations" +
                                " WHERE a.availabilityId = :availabilityId",
                        Availability.class)
                .setParameter("availabilityId", availabilityId)
                .getResultStream()
                .findFirst();
    }

    public void deleteAllOverlapping(Tutor tutor, LocalDateTime start, LocalDateTime end) {
        em.createQuery("DELETE FROM Availability a WHERE a.tutor = :tutor AND a.startDateTime < :end AND a.endDateTime > :start")
                .setParameter("tutor", tutor)
                .setParameter("start", start)
                .setParameter("end", end)
                .executeUpdate();
    }


    public List<Availability> fetchOverlapping(Tutor tutor, LocalDateTime start, LocalDateTime end) {
        TypedQuery<Availability> query = em.createQuery(
                "FROM Availability a " +
                        "WHERE a.tutor = :tutor AND a.startDateTime < :end AND a.endDateTime > :start " +
                        "ORDER BY a.startDateTime ASC",
                Availability.class
        );
        query.setParameter("tutor", tutor);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }


    public void insertIfNoConflicts(Availability availability) {
        boolean overlappingAbsenceExist = em.createQuery(
                        "SELECT COUNT(a) > 0 FROM Absence a " +
                                "WHERE a.tutor = :tutor AND a.startDate < :end AND a.endDate > :start",
                        Boolean.class)
                .setParameter("tutor", availability.getTutor())
                .setParameter("start", availability.getStartDateTime())
                .setParameter("end", availability.getEndDateTime())
                .getSingleResult();
        if (overlappingAbsenceExist) return;

        boolean overlappingReservationExists = em.createQuery(
                        "SELECT COUNT(r) > 0 FROM Reservation r " +
                                "WHERE r.tutor = :tutor AND r.startDateTime < :end AND r.endDateTime > :start " +
                                "AND r.status != :status",
                        Boolean.class)
                .setParameter("tutor", availability.getTutor())
                .setParameter("start", availability.getStartDateTime())
                .setParameter("end", availability.getEndDateTime())
                .setParameter("status", ReservationStatus.CANCELLED)
                .getSingleResult();
        if (overlappingReservationExists) return;

        em.persist(availability);
    }

    public List<Availability> findAvailabilitiesByTutorAndTimeFrame(Tutor tutor, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return em.createQuery("FROM Availability a WHERE a.tutor = :tutor AND a.startDateTime >= :startDateTime AND a.endDateTime <= :endDateTime", Availability.class)
                .setParameter("tutor", tutor)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }

}
