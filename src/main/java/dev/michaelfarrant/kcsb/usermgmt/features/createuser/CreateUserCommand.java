package dev.michaelfarrant.kcsb.usermgmt.features.createuser;

import dev.michaelfarrant.kcsb.core.cqrs.Command;

public record CreateUserCommand(String firstName, String lastName, String email) implements Command {
}
