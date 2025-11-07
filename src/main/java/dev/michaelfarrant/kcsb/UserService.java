package dev.michaelfarrant.kcsb;

import jakarta.ws.rs.NotFoundException;
import org.apache.http.HttpStatus;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final KeycloakConfiguration keycloakConfiguration;
    private final Keycloak keycloakClient;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(Keycloak keycloakClient, KeycloakConfiguration keycloakConfiguration) {
        this.keycloakClient = keycloakClient;
        this.keycloakConfiguration = keycloakConfiguration;
    }

    public UUID createUser(CreateUserRequest command){

        RealmResource realmResource = keycloakClient.realm(keycloakConfiguration.realm());
        UserRepresentation user = new UserRepresentation();

        user.setFirstName(command.firstName());
        user.setLastName(command.lastName());
        user.setEmail(command.email());
        user.setUsername(command.email());
        user.setEmailVerified(true);
        user.setEnabled(true);

        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setType("password");
        passwordCredential.setTemporary(false);
        passwordCredential.setValue("password123");

        List<CredentialRepresentation> credentials = new ArrayList<>();
        credentials.add(passwordCredential);

        user.setCredentials(credentials);

        try(var createUserResponse = realmResource.users().create(user)){

            if(createUserResponse.getStatus() != HttpStatus.SC_CREATED) {
                logger.info("Failed to create user. Http response code: {}", createUserResponse.getStatus());
                throw new RuntimeException("Failed to create user in keycloak");
            }

            String path = createUserResponse.getLocation().getPath();
            String userIdSegment = path.substring(path.lastIndexOf('/') + 1);
            return UUID.fromString(userIdSegment);
        }

    }

    public Optional<User> getUser(UUID userId){

        RealmResource realmResource = keycloakClient.realm(keycloakConfiguration.realm());
        UserResource resource = realmResource.users().get(userId.toString());

        try{
            UserRepresentation representation = resource.toRepresentation();
            User user = new User(userId, representation.getFirstName(), representation.getLastName(), representation.getEmail());
            return Optional.of(user);
        }
        catch(NotFoundException notFoundException){
            return Optional.empty();
        }

    }

}
