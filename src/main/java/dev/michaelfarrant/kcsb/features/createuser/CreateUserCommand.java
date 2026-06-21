package dev.michaelfarrant.kcsb.features.createuser;

import dev.michaelfarrant.kcsb.core.cqrs.Command;

public record CreateUserCommand(String firstName, String lastName, String email) implements Command {
}
