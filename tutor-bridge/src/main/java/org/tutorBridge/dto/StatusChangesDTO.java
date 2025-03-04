package org.tutorBridge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class StatusChangesDTO {
    @NotNull(message = "Changes are required")
    private List<@Valid StatusChangeDTO> changes;

    public StatusChangesDTO() {
    }

    public List<StatusChangeDTO> getChanges() {
        return changes;
    }

    public void setChanges(List<StatusChangeDTO> changes) {
        this.changes = changes;
    }
}
