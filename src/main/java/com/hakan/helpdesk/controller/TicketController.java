package com.hakan.helpdesk.controller;

import com.hakan.helpdesk.dto.TicketAdminUpdateRequest;
import com.hakan.helpdesk.dto.TicketDetailResponse;
import com.hakan.helpdesk.dto.TicketRequest;
import com.hakan.helpdesk.dto.TicketSummaryResponse;
import com.hakan.helpdesk.dto.TicketUserUpdateRequest;
import com.hakan.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // Yeni ticket oluştur
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<TicketDetailResponse> createTicket(
            @Valid @RequestBody TicketRequest request,
            Authentication authentication) {

        String username = authentication.getName();
        TicketDetailResponse response = ticketService.createTicket(request, username);
        return ResponseEntity.ok(response);
    }

    // Tüm ticket’ları listele
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TicketSummaryResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    // Ticket detayını getir
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailResponse> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    // USER kendi ticketını günceller
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/user-update")
    public ResponseEntity<TicketDetailResponse> updateTicketByUser(
            @PathVariable Long id,
            @Valid @RequestBody TicketUserUpdateRequest request,
            Authentication auth) {
        return ResponseEntity.ok(ticketService.updateTicketByUser(id, request, auth.getName()));
    }

    // ADMIN herhangi bir ticketı günceller
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/admin-update")
    public ResponseEntity<TicketDetailResponse> updateTicketByAdmin(
            @PathVariable Long id,
            @Valid @RequestBody TicketAdminUpdateRequest request) {
        return ResponseEntity.ok(ticketService.updateTicketByAdmin(id, request));
    }

    // Ticket sil
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id, Authentication auth) {
        ticketService.deleteTicket(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    // Ticket arama
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<Page<TicketSummaryResponse>> searchTickets(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String assignedTo,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort,
            Authentication auth) {
        String username = auth.getName();
        String[] sortParams = sort.split(",");
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]));

        return ResponseEntity
                .ok(ticketService.searchTickets(status, priority, assignedTo, keyword, username, pageable));
    }

}
