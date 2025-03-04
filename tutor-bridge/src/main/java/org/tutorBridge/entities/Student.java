package org.tutorBridge.entities;

import jakarta.persistence.*;
import org.tutorBridge.entities.enums.StudentLevel;
import org.tutorBridge.entities.enums.UserType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
@PrimaryKeyJoinColumn(name = "STUDENTID", referencedColumnName = "USERID")
public class Student extends User {
    @Enumerated(EnumType.STRING)
    @Column(name = "\"level\"", nullable = false, length = 100)
    private StudentLevel level;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    public Student() {
    }

    public Student(String firstName,
                   String lastName,
                   String phone,
                   String email,
                   String password,
                   StudentLevel level,
                   LocalDate birthDate) {
        super(firstName, lastName, phone, email, password, UserType.STUDENT, birthDate);
        this.level = level;
    }

    public StudentLevel getLevel() {
        return level;
    }

    public void setLevel(StudentLevel level) {
        this.level = level;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setStudent(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setStudent(null);
    }
}
