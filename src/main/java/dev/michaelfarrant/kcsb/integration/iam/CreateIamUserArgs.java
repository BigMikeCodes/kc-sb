package dev.michaelfarrant.kcsb.integration.iam;

import java.util.UUID;

public record CreateIamUserArgs(
        String firstName,
        String lastName,
        String email,
        UUID applicationId) {
}
