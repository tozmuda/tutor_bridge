package org.tutorBridge.dto;

import java.util.List;

public record PlanResponseDTO
        (List<PlanEntryDTO> reservations) {
}
