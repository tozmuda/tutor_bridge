package org.tutorBridge.dto;

import org.tutorBridge.validation.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public final class TimeFrameDTO {
    private LocalDateTime start;
    private LocalDateTime end;

    public TimeFrameDTO(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public TimeFrameDTO() {
    }

    public static LocalDateTime getDefaultStart() {
        return LocalDateTime.now();
    }

    public static LocalDateTime getDefaultEnd() {
        return LocalDate.of(9999, 12, 31).atStartOfDay();
    }

    public static TimeFrameDTO fillInEmptyFields(TimeFrameDTO timeFrame) {
        if (timeFrame == null) {
            timeFrame = new TimeFrameDTO(null, null);
        }
        if (timeFrame.getStart() == null) {
            timeFrame.setStart(getDefaultStart());
        }
        if (timeFrame.getEnd() == null) {
            timeFrame.setEnd(getDefaultEnd());
        }
        if (timeFrame.getStart().isAfter(timeFrame.getEnd())) {
            throw new ValidationException("Start time must be before end time");
        }
        return timeFrame;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TimeFrameDTO) obj;
        return Objects.equals(this.start, that.start) &&
                Objects.equals(this.end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
