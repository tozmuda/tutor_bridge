package org.tutorBridge.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;
import org.tutorBridge.entities.enums.UserType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TUTOR")
@PrimaryKeyJoinColumn(name = "TUTORID")
public class Tutor extends User {

    @ManyToMany
    @JoinTable(
            name = "TUTORSPECIALIZATIONS",
            joinColumns = @JoinColumn(name = "TUTORID"),
            inverseJoinColumns = @JoinColumn(name = "SPECIALIZATIONID")
    )
    private final Set<Specialization> specializations = new HashSet<>();
    @Column(name = "BIO", length = 4000, nullable = false)
    @Nationalized
    private String bio;
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Absence> absences = new HashSet<>();

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Availability> availabilities = new HashSet<>();

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    public Tutor() {
    }

    public Tutor(String firstName,
                 String lastName,
                 String phone,
                 String email,
                 String password,
                 String bio,
                 LocalDate birthDate) {
        super(firstName, lastName, phone, email, password, UserType.TUTOR, birthDate);
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Set<Specialization> specializations) {
        this.specializations.clear();
        this.specializations.addAll(specializations);
    }


    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public Set<Absence> getAbsences() {
        return absences;
    }


    public void addAbsence(Absence absence) {
        absences.add(absence);
    }

    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public void addAvailability(Availability availability) {
        availabilities.add(availability);
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

}
