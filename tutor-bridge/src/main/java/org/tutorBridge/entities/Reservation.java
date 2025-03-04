package org.tutorBridge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.tutorBridge.entities.enums.ReservationStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATION",
        indexes = {
                @Index(name = "Reservation_idx_studentID", columnList = "STUDENTID"),
                @Index(name = "Reservation_idx_tutorID", columnList = "TUTORID"),
                @Index(name = "Reservation_idx_specializationID", columnList = "SPECIALIZATIONID"),
                @Index(name = "Reservation_idx_startDate", columnList = "STARTDATE"),
                @Index(name = "Reservation_idx_endDate", columnList = "ENDDATE"),
                @Index(name = "Reservation_idx_status", columnList = "STATUS")
        }
)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATIONID", nullable = false)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "STUDENTID", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "TUTORID", nullable = false)
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "SPECIALIZATIONID", nullable = false)
    private Specialization specialization;

    @NotNull(message = "Start time is required")
    @Column(name = "STARTDATE", nullable = false)
    private LocalDateTime startDateTime;

    @NotNull(message = "End time is required")
    @Column(name = "ENDDATE", nullable = false)
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 10, nullable = false)
    private ReservationStatus status;
    public Reservation() {}

    public Reservation(Student student, Tutor tutor, Specialization specialization, LocalDateTime start, LocalDateTime end) {
        this.student = student;
        this.tutor = tutor;
        this.specialization = specialization;
        this.startDateTime = start;
        this.endDateTime = end;
        this.status = ReservationStatus.NEW;
    }


    public Long getReservationId() {
        return reservationId;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
