package com.hakan.helpdesk.dto;

import java.time.LocalDateTime;

public record TicketDetailResponse(
                Long id,
                String title,
                String description,
                String status,
                String priority,
                String createdBy,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
