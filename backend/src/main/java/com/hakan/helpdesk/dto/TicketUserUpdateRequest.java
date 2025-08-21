package com.hakan.helpdesk.dto;

import jakarta.validation.constraints.NotBlank;

public record TicketUserUpdateRequest(
        @NotBlank String title,
        @NotBlank String description) {
}
