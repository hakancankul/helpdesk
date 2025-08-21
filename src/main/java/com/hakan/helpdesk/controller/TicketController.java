package com.hakan.helpdesk.controller;

import com.hakan.helpdesk.dto.TicketAdminUpdateRequest;
import com.hakan.helpdesk.dto.TicketDetailResponse;
import com.hakan.helpdesk.dto.TicketRequest;
import com.hakan.helpdesk.dto.TicketSummaryResponse;
import com.hakan.helpdesk.dto.TicketUserUpdateRequest;
import com.hakan.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    // 1. Yeni ticket oluştur
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<TicketDetailResponse> createTicket(@Valid @RequestBody TicketRequest request,
            Authentication auth) {
        return ResponseEntity.ok(ticketService.createTicket(request, auth.getName()));
    }

    // 2. Tüm ticket’ları listele
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TicketSummaryResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    // 3. Ticket detayını getir
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

    // 5. Ticket sil
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id, Authentication auth) {
        ticketService.deleteTicket(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
