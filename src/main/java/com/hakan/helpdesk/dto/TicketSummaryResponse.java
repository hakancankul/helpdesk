package com.hakan.helpdesk.dto;

import java.time.LocalDateTime;

public record TicketSummaryResponse(
        Long id,
        String title,
        String status,
        String priority,
        String createdBy,
        LocalDateTime createdAt) {
}
