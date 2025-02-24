package dev.michaelfarrant.kcsb;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest request) {
        UUID userId = userService.createUser(request);
        URI location = linkTo(methodOn(UserController.class).getUser(userId)).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("api/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") UUID userId){
        Optional<User> user = userService.getUser(userId);
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("api/whoami")
    public ResponseEntity<String> whoAmI(JwtAuthenticationToken principal){
        return ResponseEntity.ok(principal.getName());
    }

}
