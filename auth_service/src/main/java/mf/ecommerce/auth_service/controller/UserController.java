package mf.ecommerce.auth_service.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mf.ecommerce.auth_service.dto.AuthUserRequestDto;
import mf.ecommerce.auth_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createAuthUser(@Valid @RequestBody AuthUserRequestDto dto) {
        userService.createKeycloakUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{email}")
    public ResponseEntity<Void> validateAuthUser(@PathVariable String email) {
        userService.validateUser(email);
        return ResponseEntity.ok().build();
    }

}
