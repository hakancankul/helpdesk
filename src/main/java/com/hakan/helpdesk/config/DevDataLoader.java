package com.hakan.helpdesk.config;

import com.hakan.helpdesk.model.Role;
import com.hakan.helpdesk.model.Ticket;
import com.hakan.helpdesk.model.User;
import com.hakan.helpdesk.repository.RoleRepository;
import com.hakan.helpdesk.repository.TicketRepository;
import com.hakan.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.hakan.helpdesk.model.Status;
import com.hakan.helpdesk.model.Priority;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DevDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TicketRepository ticketRepository;
    private final PasswordEncoder passwordEncoder;

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

        // --- USER ROLE + USER ---
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "USER")));

        User normalUser = userRepository.findByUsername("user")
                .orElseGet(() -> {
                    User u = User.builder()
                            .username("user")
                            .password(passwordEncoder.encode("user123"))
                            .roles(Set.of(userRole))
                            .build();
                    return userRepository.save(u);
                });

        // --- TICKETS ---
        if (ticketRepository.count() == 0) {
            List<Ticket> tickets = List.of(
                    Ticket.builder()
                            .title("Login Issue")
                            .description("Cannot log into the system")
                            .status(Status.OPEN)
                            .priority(Priority.HIGH)
                            .createdBy(normalUser)
                            .build(),
                    Ticket.builder()
                            .title("Page Not Loading")
                            .description("Dashboard page stuck")
                            .status(Status.OPEN)
                            .priority(Priority.MEDIUM)
                            .createdBy(normalUser)
                            .build(),
                    Ticket.builder()
                            .title("Password Reset")
                            .description("Forgot password link not working")
                            .status(Status.OPEN)
                            .priority(Priority.LOW)
                            .createdBy(normalUser)
                            .build());
            ticketRepository.saveAll(tickets);
        }

    }
}
