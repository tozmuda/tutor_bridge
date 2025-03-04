package org.tutorBridge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SPECIALIZATION",
        uniqueConstraints = @UniqueConstraint(columnNames = "NAME")
)
public class Specialization {

    @ManyToMany(mappedBy = "specializations")
    private final Set<Tutor> tutors = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPECIALIZATIONID", nullable = false)
    private Long specializationId;
    @NotBlank(message = "Specialization name is required")
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;


    public Specialization() {
    }

    public Specialization(String name) {
        this.name = name;
    }


    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Tutor> getTutors() {
        return tutors;
    }

}
