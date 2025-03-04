package org.tutorBridge.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.tutorBridge.entities.User;
import org.tutorBridge.security.JwtTokenUtil;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserDaoTest {

    @Autowired
    private UserRepo userDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    void testEntityManagerInjection() {
        assertNotNull(em);  // Ensure EntityManager is not null
        List<User> users = em.createQuery("from User", User.class).getResultList();
        assertNotNull(users);  // Ensure query returns a result
    }

    @Test
    void findByEmail() {
        Optional<User> user = userDao.findByEmail("bob@gmail.com");
        assertTrue(user.isPresent());


    }


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void testAuthenticationManagerInjection() {
        assertNotNull(authenticationManager);  // Ensure AuthenticationManager is not null
    }

    @Test
    void testJwtTokenUtilInjection() {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("a@b.c", "123321"));

        var user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        System.out.println(user.getUsername());
        User u  = userDao.findByEmail(user.getUsername()).orElseThrow();

        String token = jwtTokenUtil.generateToken(u.getEmail());

        assertNotNull(token);  // Ensure token is not null
    }

}
