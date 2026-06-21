package dev.michaelfarrant.kcsb.features.createuser;

import dev.michaelfarrant.kcsb.core.cqrs.CommandHandler;
import dev.michaelfarrant.kcsb.integration.iam.IamUserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, CreateUserResult> {

    private final IamUserService iamUserService;

    public CreateUserCommandHandler(IamUserService iamUserService) {
        this.iamUserService = iamUserService;
    }

    @Override
    public CreateUserResult handleCommand(CreateUserCommand command) {
        // Implement the logic to create a user here
        String userId = "user-id-placeholder";
        return new CreateUserResult.Success(UUID.randomUUID());
    }
}
