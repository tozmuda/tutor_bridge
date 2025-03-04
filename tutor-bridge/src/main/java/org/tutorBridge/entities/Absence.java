package org.tutorBridge.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.tutorBridge.validation.ValidAbsenceRange;

import java.time.LocalDateTime;

@Entity
@ValidAbsenceRange
@Table(name = "Absence",
        indexes = {
                @Index(name = "Absence_idx_tutorID", columnList = "TUTORID"),
                @Index(name = "Absence_idx_startDate", columnList = "STARTDATE"),
                @Index(name = "Absence_idx_endDate", columnList = "ENDDATE")
        }
)
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ABSENCEID", nullable = false)
    private Long absenceId;

    @ManyToOne
    @JoinColumn(name = "TUTORID", nullable = false)
    private Tutor tutor;

    @NotNull(message = "Start date is required")
    @Column(name = "STARTDATE", nullable = false)
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    @Column(name = "ENDDATE", nullable = false)
    private LocalDateTime endDate;

    public Absence() {
    }

    public Absence(Tutor tutor, LocalDateTime startDate, LocalDateTime endDate) {
        this.tutor = tutor;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getAbsenceId() {
        return absenceId;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

}
