package org.tutorBridge.dto;


import jakarta.validation.constraints.NotNull;

public class NewReservationDTO {
    private Long reservationId;
    @NotNull(message = "Availability ID is required")
    private Long availabilityId;

    @NotNull(message = "Specialization ID is required")
    private String specializationName;

    public NewReservationDTO() {
    }

    public Long getAvailabilityId() {
        return availabilityId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}
