package mf.ecommerce.auth_service.controller;

import lombok.AllArgsConstructor;
import mf.ecommerce.auth_service.dto.AuthRequestDto;
import mf.ecommerce.auth_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createAuthUser(@RequestBody AuthRequestDto dto) {
        userService.createKeycloakUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
