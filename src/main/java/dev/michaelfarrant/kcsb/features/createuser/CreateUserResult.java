package dev.michaelfarrant.kcsb.features.createuser;

import java.util.UUID;

public sealed interface CreateUserResult permits CreateUserResult.Success, CreateUserResult.Failure {
    record Success(UUID userId) implements CreateUserResult {}

    record Failure(String errorMessage) implements CreateUserResult {}
}
