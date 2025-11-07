package dev.michaelfarrant.kcsb;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakBeanConfig {

    private final KeycloakConfiguration keycloakConfiguration;

    public KeycloakBeanConfig(KeycloakConfiguration keycloakConfiguration) {
        this.keycloakConfiguration = keycloakConfiguration;
    }

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder
                .builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .serverUrl(keycloakConfiguration.url())
                .realm(keycloakConfiguration.realm())
                .clientId(keycloakConfiguration.clientId())
                .clientSecret(keycloakConfiguration.clientSecret())
                .build();
    }

}
