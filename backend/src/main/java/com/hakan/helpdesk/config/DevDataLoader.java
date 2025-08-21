package com.hakan.helpdesk.config;

import com.hakan.helpdesk.model.*;
import com.hakan.helpdesk.repository.RoleRepository;
import com.hakan.helpdesk.repository.TicketRepository;
import com.hakan.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DevDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TicketRepository ticketRepository;
    private final PasswordEncoder passwordEncoder;

    private final Random random = new Random();

    @Override
    public void run(String... args) {
        // --- ADMIN ROLE + USER ---
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN")));

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(adminRole))
                    .build();
            userRepository.save(admin);
        }

        // --- USER ROLE + USERS ---
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "USER")));

        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String username = "user" + i;
            User user = userRepository.findByUsername(username)
                    .orElseGet(() -> {
                        User u = User.builder()
                                .username(username)
                                .password(passwordEncoder.encode("1234"))
                                .roles(Set.of(userRole))
                                .build();
                        return userRepository.save(u);
                    });
            users.add(user);
        }

        // --- TICKETS ---
        if (ticketRepository.count() == 0) {
            String[] titles = {
                    "Login Issue", "Page Not Loading", "Password Reset", "Email Not Received",
                    "Database Error", "API Timeout", "UI Bug", "Payment Failed",
                    "Search Not Working", "File Upload Error", "Notification Delay",
                    "Access Denied", "Report Generation Failed", "Slow Performance", "Data Sync Issue"
            };

            String[] descriptions = {
                    "User cannot log in.", "Page stuck while loading.", "Forgot password link not working.",
                    "Verification email not received.", "Error in connecting to DB.", "External API timeout.",
                    "UI alignment broken.", "Payment not processed.", "Search gives no results.",
                    "File upload failing.", "Notifications arrive late.", "User denied access unexpectedly.",
                    "Report not generated.", "System slow under load.", "Data not syncing properly."
            };

            List<Ticket> tickets = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                User createdBy = users.get(random.nextInt(users.size()));
                Status status = Status.values()[random.nextInt(Status.values().length)];
                Priority priority = Priority.values()[random.nextInt(Priority.values().length)];

                Ticket ticket = Ticket.builder()
                        .title(titles[i])
                        .description(descriptions[i])
                        .status(status)
                        .priority(priority)
                        .createdBy(createdBy)
                        .build();

                tickets.add(ticket);
            }
            ticketRepository.saveAll(tickets);
        }
    }
}
