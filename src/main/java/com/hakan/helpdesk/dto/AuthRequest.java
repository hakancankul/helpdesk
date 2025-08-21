package com.hakan.helpdesk.dto;

public record AuthRequest(
        String username,
        String password) {
}
