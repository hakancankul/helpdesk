package com.hakan.helpdesk.dto;

import com.hakan.helpdesk.model.Priority;
import com.hakan.helpdesk.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull Status status,
        @NotNull Priority priority,
        Long assignedToId) {
}
