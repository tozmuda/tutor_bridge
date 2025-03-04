package org.tutorBridge.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AbsenceDTO {
    private Long absenceId;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the future")
    private LocalDateTime start;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in the future")
    private LocalDateTime end;

    public AbsenceDTO() {
    }

    public AbsenceDTO(Long absenceId, LocalDateTime start, LocalDateTime endDate) {
        this.absenceId = absenceId;
        this.start = start;
        this.end = endDate;
    }

    public AbsenceDTO(LocalDateTime start, LocalDateTime endDate) {
        this(null, start, endDate);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Long getAbsenceId() {
        return absenceId;
    }

    public void setAbsenceId(Long absenceId) {
        this.absenceId = absenceId;
    }
}
