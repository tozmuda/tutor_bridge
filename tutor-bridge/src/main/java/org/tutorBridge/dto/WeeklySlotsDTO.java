package org.tutorBridge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class WeeklySlotsDTO implements Serializable {
    @NotNull(message = "Weekly time ranges are required")
    private Map<DayOfWeek, List<@Valid TimeRangeDTO>> weeklyTimeRanges;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in the future")
    private LocalDate endDate;


    public WeeklySlotsDTO() { }

    public Map<DayOfWeek, List<TimeRangeDTO>> getWeeklyTimeRanges() {
        return weeklyTimeRanges;
    }

    public LocalDate getStartDate() {
        return startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

}
