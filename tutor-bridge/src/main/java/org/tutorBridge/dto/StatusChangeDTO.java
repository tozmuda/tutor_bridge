package org.tutorBridge.dto;

import jakarta.validation.constraints.NotNull;
import org.tutorBridge.entities.enums.ReservationStatus;

public class StatusChangeDTO {
    @NotNull(message = "Reservation ID is required")
    private Long reservationId;
    @NotNull(message = "Status is required")
    private ReservationStatus status;


    public StatusChangeDTO() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
