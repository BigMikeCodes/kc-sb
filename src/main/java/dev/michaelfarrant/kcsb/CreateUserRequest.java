package dev.michaelfarrant.kcsb;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank
        @Size(min = 1, max = 50)
        String firstName,

        @NotBlank
        @Size(min = 1, max = 50)
        String lastName,

        @NotBlank
        @Size(min = 1, max = 100)
        String email) {
}
