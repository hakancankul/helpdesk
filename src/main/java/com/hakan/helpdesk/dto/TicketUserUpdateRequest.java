package com.hakan.helpdesk.dto;

import com.hakan.helpdesk.model.Priority;

public record TicketUserUpdateRequest(
        String title,
        String description,
        Priority priority) {
}
