package org.tutorBridge.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.tutorBridge.entities.User;

import java.util.Optional;

@Repository
public class UserRepo extends GenericRepo<User, Long> {
    @PersistenceContext
    private EntityManager em;

    public UserRepo() {
        super(User.class);
    }



    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = em.createQuery("from User where email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }
}
