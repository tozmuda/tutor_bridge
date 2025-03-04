package org.tutorBridge.dto;


import jakarta.validation.constraints.NotNull;
import org.tutorBridge.validation.ValidTimeRangeDTO;

import java.io.Serializable;
import java.time.LocalTime;

@ValidTimeRangeDTO
public class TimeRangeDTO implements Serializable {
    @NotNull(message = "Start time is required")
    private LocalTime start;
    @NotNull(message = "End time is required")
    private LocalTime end;

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeRangeDTO that = (TimeRangeDTO) o;

        if (getStart() != null ? !getStart().equals(that.getStart()) : that.getStart() != null) return false;
        return getEnd() != null ? getEnd().equals(that.getEnd()) : that.getEnd() == null;
    }

    @Override
    public int hashCode() {
        int result = getStart() != null ? getStart().hashCode() : 0;
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        return result;
    }
}
