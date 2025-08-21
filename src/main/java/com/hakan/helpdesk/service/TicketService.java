package com.hakan.helpdesk.service;

import com.hakan.helpdesk.dto.TicketRequest;
import com.hakan.helpdesk.dto.TicketResponse;

import java.util.List;

public interface TicketService {
    TicketResponse createTicket(TicketRequest request);
    List<TicketResponse> getAllTickets();
    TicketResponse getTicketById(Long id);
    TicketResponse updateTicket(Long id, TicketRequest request);
    void deleteTicket(Long id);
}
