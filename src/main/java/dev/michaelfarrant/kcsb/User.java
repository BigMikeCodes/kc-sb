package dev.michaelfarrant.kcsb;

import java.util.UUID;

public record User(
        UUID id,
        String firstName,
        String lastName,
        String email) {
}
