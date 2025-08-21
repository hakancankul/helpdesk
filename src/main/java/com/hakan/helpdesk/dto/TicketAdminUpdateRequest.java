package com.hakan.helpdesk.dto;

import com.hakan.helpdesk.model.Status;
import com.hakan.helpdesk.model.Priority;

public record TicketAdminUpdateRequest(
        Status status,
        Priority priority) {
}
