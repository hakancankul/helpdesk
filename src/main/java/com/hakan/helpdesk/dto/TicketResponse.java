package com.hakan.helpdesk.dto;

import com.hakan.helpdesk.model.Priority;
import com.hakan.helpdesk.model.Status;

import java.time.LocalDateTime;

public record TicketResponse(
        Long id,
        String title,
        String description,
        Status status,
        Priority priority,
        String createdBy,
        String assignedTo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
