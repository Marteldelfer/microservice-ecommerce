package mf.ecommerce.auth_service.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mf.ecommerce.auth_service.dto.LoginRequestDto;
import mf.ecommerce.auth_service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.createToken(dto));
    }

    @GetMapping("/validate/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> validateCostumer() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate/provider")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> validateProvider() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> validateAdmin() {
        return ResponseEntity.ok().build();
    }

}
