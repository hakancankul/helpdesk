package com.hakan.helpdesk.service.impl;

import com.hakan.helpdesk.dto.TicketRequest;
import com.hakan.helpdesk.dto.TicketResponse;
import com.hakan.helpdesk.model.Ticket;
import com.hakan.helpdesk.model.User;
import com.hakan.helpdesk.repository.TicketRepository;
import com.hakan.helpdesk.repository.UserRepository;
import com.hakan.helpdesk.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        User creator = userRepository.findById(request.createdById())
                .orElseThrow(() -> new RuntimeException("User not found"));
        User assignee = request.assignedToId() != null
                ? userRepository.findById(request.assignedToId()).orElse(null)
                : null;

        Ticket ticket = Ticket.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .priority(request.priority())
                .createdBy(creator)
                .assignedTo(assignee)
                .build();

        Ticket saved = ticketRepository.save(ticket);

        return toResponse(saved);
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return toResponse(ticket);
    }

    @Override
    public TicketResponse updateTicket(Long id, TicketRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setTitle(request.title());
        ticket.setDescription(request.description());
        ticket.setStatus(request.status());
        ticket.setPriority(request.priority());

        if (request.assignedToId() != null) {
            User assignee = userRepository.findById(request.assignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            ticket.setAssignedTo(assignee);
        }

        Ticket updated = ticketRepository.save(ticket);
        return toResponse(updated);
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    private TicketResponse toResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getCreatedBy().getUsername(),
                ticket.getAssignedTo() != null ? ticket.getAssignedTo().getUsername() : null,
                ticket.getCreatedAt(),
                ticket.getUpdatedAt());
    }
}
