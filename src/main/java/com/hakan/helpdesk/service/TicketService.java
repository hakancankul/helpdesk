package com.hakan.helpdesk.service;

import com.hakan.helpdesk.dto.TicketRequest;
import com.hakan.helpdesk.dto.TicketAdminUpdateRequest;
import com.hakan.helpdesk.dto.TicketDetailResponse;
import com.hakan.helpdesk.dto.TicketSummaryResponse;
import com.hakan.helpdesk.dto.TicketUserUpdateRequest;

import java.util.List;

public interface TicketService {
    TicketDetailResponse createTicket(TicketRequest request, String username);

    List<TicketSummaryResponse> getAllTickets();

    TicketDetailResponse getTicketById(Long id);

    TicketDetailResponse updateTicketByUser(Long id, TicketUserUpdateRequest request, String username);

    TicketDetailResponse updateTicketByAdmin(Long id, TicketAdminUpdateRequest request);

    void deleteTicket(Long id, String username);
}
