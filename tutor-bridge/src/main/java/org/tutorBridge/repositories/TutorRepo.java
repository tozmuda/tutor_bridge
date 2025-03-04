package org.tutorBridge.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.tutorBridge.entities.Absence;
import org.tutorBridge.entities.Reservation;
import org.tutorBridge.entities.Tutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TutorRepo extends GenericRepo<Tutor, Long> {

    @PersistenceContext
    private EntityManager em;

    public TutorRepo() {
        super(Tutor.class);
    }

    public Optional<Tutor> findByEmail(String email) {
        TypedQuery<Tutor> query = em.createQuery("from Tutor where email = :email", Tutor.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }


    public List<Tutor> findTutorsWithAvailabilities(String specializationName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return em.createQuery(
                        "SELECT DISTINCT t FROM Tutor t " +
                                "JOIN t.specializations s " +
                                "JOIN FETCH t.availabilities a " +
                                "WHERE lower(s.name) = lower(:specializationName) " +
                                "AND (a.startDateTime >= :startDateTime AND a.endDateTime <= :endDateTime OR a.availabilityId IS NULL)",
                        Tutor.class)
                .setParameter("specializationName", specializationName.toLowerCase())
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }


}
