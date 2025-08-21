package com.hakan.helpdesk.service.impl;

import com.hakan.helpdesk.dto.TicketAdminUpdateRequest;
import com.hakan.helpdesk.dto.TicketDetailResponse;
import com.hakan.helpdesk.dto.TicketRequest;
import com.hakan.helpdesk.dto.TicketSummaryResponse;
import com.hakan.helpdesk.dto.TicketUserUpdateRequest;
import com.hakan.helpdesk.model.Ticket;
import com.hakan.helpdesk.model.User;
import com.hakan.helpdesk.repository.TicketRepository;
import com.hakan.helpdesk.repository.UserRepository;
import com.hakan.helpdesk.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public TicketDetailResponse createTicket(TicketRequest request, String username) {
        User createdBy = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User assignedTo = null;
        if (request.assignedToId() != null) {
            assignedTo = userRepository.findById(request.assignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        }

        Ticket ticket = Ticket.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .priority(request.priority())
                .createdBy(createdBy)
                .assignedTo(assignedTo)
                .build();

        Ticket saved = ticketRepository.save(ticket);

        return toDetailResponse(saved);
    }

    @Override
    public List<TicketSummaryResponse> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    @Override
    public TicketDetailResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !ticket.getCreatedBy().getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You are not allowed to view this ticket");
        }

        return toDetailResponse(ticket);
    }

    @Override
    public TicketDetailResponse updateTicketByUser(Long id, TicketUserUpdateRequest request, String username) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getCreatedBy().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only update your own tickets");
        }

        ticket.setTitle(request.title());
        ticket.setDescription(request.description());
        ticket.setPriority(request.priority());
        ticket.setUpdatedAt(LocalDateTime.now());

        return toDetailResponse(ticketRepository.save(ticket));
    }

    @Override
    public TicketDetailResponse updateTicketByAdmin(Long id, TicketAdminUpdateRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(request.status());
        ticket.setPriority(request.priority());
        ticket.setUpdatedAt(LocalDateTime.now());

        return toDetailResponse(ticketRepository.save(ticket));
    }

    @Override
    public void deleteTicket(Long id, String username) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ADMIN"));

        // admin ise her şeyi silebilir, user sadece kendi oluşturduğu ticket'ı
        if (isAdmin || ticket.getCreatedBy().getUsername().equals(username)) {
            ticketRepository.delete(ticket);
        } else {
            throw new AccessDeniedException("You cannot delete this ticket");
        }
    }

    private TicketSummaryResponse toSummaryResponse(Ticket ticket) {
        return new TicketSummaryResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getStatus().name(),
                ticket.getPriority().name(),
                ticket.getCreatedBy().getUsername(),
                ticket.getCreatedAt());
    }

    private TicketDetailResponse toDetailResponse(Ticket ticket) {
        return new TicketDetailResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus().name(),
                ticket.getPriority().name(),
                ticket.getCreatedBy().getUsername(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt());
    }

}
