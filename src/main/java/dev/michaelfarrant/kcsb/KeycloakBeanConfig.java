package dev.michaelfarrant.kcsb;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakBeanConfig {

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.clientSecret}")
    private String clientSecret;

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder
                .builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .serverUrl(keycloakUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

}
