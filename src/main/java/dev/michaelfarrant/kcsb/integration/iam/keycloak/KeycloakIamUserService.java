package dev.michaelfarrant.kcsb.integration.iam.keycloak;

import dev.michaelfarrant.kcsb.KeycloakConfiguration;
import dev.michaelfarrant.kcsb.integration.iam.CreateIamUserArgs;
import dev.michaelfarrant.kcsb.integration.iam.CreateIamUserResult;
import dev.michaelfarrant.kcsb.integration.iam.IamUserService;
import org.apache.http.HttpStatus;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeycloakIamUserService implements IamUserService {

    private final KeycloakConfiguration keycloakConfiguration;
    private final Keycloak keycloakClient;
    private final Logger logger = LoggerFactory.getLogger(KeycloakIamUserService.class);

    public KeycloakIamUserService(
            KeycloakConfiguration keycloakConfiguration,
            Keycloak keycloakClient) {
        this.keycloakConfiguration = keycloakConfiguration;
        this.keycloakClient = keycloakClient;
    }

    @Override
    public CreateIamUserResult createUser(CreateIamUserArgs args) {
        RealmResource realmResource = keycloakClient.realm(keycloakConfiguration.realm());
        UserRepresentation user = new UserRepresentation();

        user.setFirstName(args.firstName());
        user.setLastName(args.lastName());
        user.setEmail(args.email());
        user.setUsername(args.email());
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

            return new CreateIamUserResult(userIdSegment);
        }
    }
}
