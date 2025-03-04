package org.tutorBridge.dto;

import java.util.List;

public record TutorSearchResultDTO(
        Long tutorId,
        String firstName,
        String lastName,
        String phone,
        String email,
        String bio,
        List<AvailabilityDTO> availableTimeSlots
) {
}
