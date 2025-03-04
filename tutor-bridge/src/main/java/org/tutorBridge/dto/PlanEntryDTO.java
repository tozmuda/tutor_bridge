package org.tutorBridge.dto;

import java.time.LocalDateTime;

public record PlanEntryDTO(
        Long reservationId,
        Long tutorId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String studentFirstName,
        String studentLastName,
        String studentPhone,
        String studentEmail,
        String tutorFirstName,
        String tutorLastName,
        String tutorPhone,
        String tutorEmail,
        String specialization,
        String studentLevel,
        String status
) {
}

