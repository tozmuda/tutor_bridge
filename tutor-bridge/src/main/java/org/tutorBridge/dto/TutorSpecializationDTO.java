package org.tutorBridge.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Set;

public class TutorSpecializationDTO implements Serializable {
    @NotNull(message = "Specializations are required")
    @Size(max = 10, message = "You can have up to 10 specializations")
    private Set<String> specializations;


    public TutorSpecializationDTO() {
    }

    public TutorSpecializationDTO(Set<String> specializations) {
        this.specializations = specializations;
    }

    public Set<String> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Set<String> specializations) {
        this.specializations = specializations;
    }
}
