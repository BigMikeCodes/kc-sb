package dev.michaelfarrant.kcsb.features.createuser.rest;

import dev.michaelfarrant.kcsb.UserController;
import dev.michaelfarrant.kcsb.features.createuser.CreateUserCommand;
import dev.michaelfarrant.kcsb.features.createuser.CreateUserCommandHandler;
import dev.michaelfarrant.kcsb.features.createuser.CreateUserResult;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CreateUserEndpoint {

    private final CreateUserCommandHandler createUserCommandHandler;

    public CreateUserEndpoint(CreateUserCommandHandler createUserCommandHandler) {
        this.createUserCommandHandler = createUserCommandHandler;
    }

    private static CreateUserCommand toCommand(CreateUserRequest request) {
        return new CreateUserCommand(request.firstName(), request.lastName(), request.email());
    }

    @PostMapping("api/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest request) {

        CreateUserCommand command = toCommand(request);
        CreateUserResult result = createUserCommandHandler.handleCommand(command);

        switch (result) {
            case CreateUserResult.Success success -> {
                URI location = linkTo(methodOn(UserController.class).getUser(success.userId())).toUri();
                return ResponseEntity.created(location).build();
            }
            case CreateUserResult.Failure failure -> {
                // Handle failure case, e.g., return an error response
                return ResponseEntity.internalServerError().build();
            }
        }
    }
}
