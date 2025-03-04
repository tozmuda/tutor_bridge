package org.tutorBridge.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.tutorBridge.validation.ValidAvailabilityRange;

import java.time.LocalDateTime;

@Entity
@ValidAvailabilityRange
@Table(name = "AVAILABILITY",
        indexes = {
                @Index(name = "Availability_idx_tutorID", columnList = "TUTORID"),
                @Index(name = "Availability_idx_startDate", columnList = "STARTDATE"),
                @Index(name = "Availability_idx_endDate", columnList = "ENDDATE")
        }
)
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AVAILABILITYID", nullable = false)
    private Long availabilityId;

    @ManyToOne
    @JoinColumn(name = "TUTORID", nullable = false)
    private Tutor tutor;

    @NotNull(message = "Start date is required")
    @Column(name = "STARTDATE", nullable = false)
    private LocalDateTime startDateTime;


    @NotNull(message = "End date is required")
    @Column(name = "ENDDATE", nullable = false)
    private LocalDateTime endDateTime;


    public Availability() {
    }

    public Availability(Tutor tutor, LocalDateTime start, LocalDateTime end) {
        this.tutor = tutor;
        this.startDateTime = start;
        this.endDateTime = end;
    }

    public Long getAvailabilityId() {
        return availabilityId;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
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

