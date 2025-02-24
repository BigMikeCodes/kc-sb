# kc-sb #

A simple Spring Boot application to demonstrate a minimal setup for Spring Security Resource Server, Keycloak and Keycloak admin client.

Simple run the default docker compose profile to start keycloak backed by a postgres db.

```
docker compose --profile default up
```

This will import a basic realm called `test-realm` (alongside the default master realm) that contains two clients:
1. test-client - General purpose client that can be used by clients to login (Postman, insomnia etc...). Will need to enable PKCE using SHA-256 as part of the login flow.
2. service-account-client - client to provide a service account for calls to the admin api via the [api client](https://www.keycloak.org/securing-apps/admin-client). This service account was created by following the instructions from the [keycloak docs](https://www.keycloak.org/docs/latest/server_admin/index.html#_service_accounts)

The Keycloak admin console can be accessed via http://127.0.0.1:8080 using `admin` for both password & username.

[OAuth2 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html) handles authentication.

After a user has been created they can be used to authenticate to the endpoints that require authentication. All created users will have their password set to `password123`

Endpoints
- POST /api/users - Unauthenticated - creates a new user
- GET /api/whoami - Authenticated - return the subject from the currently authenticated user (keycloak userId)
- GET /api/users/{userId} - Authenticated - get user by id
