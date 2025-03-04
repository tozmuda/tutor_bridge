package org.tutorBridge.dto;


import java.time.LocalDateTime;

public class AvailabilityDTO {
    private Long availabilityId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public AvailabilityDTO() {
    }

    public AvailabilityDTO(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public AvailabilityDTO(Long availabilityId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.availabilityId = availabilityId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
