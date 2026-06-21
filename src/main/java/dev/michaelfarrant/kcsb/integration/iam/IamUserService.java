package dev.michaelfarrant.kcsb.integration.iam;

/**
 * Contract for integrating with external providers. I.e: keycloak, aws cognito, okta etc....
 */
public interface IamUserService {

    CreateIamUserResult createUser(CreateIamUserArgs args);

}
