package com.retailbookshop.auth;

import com.retailbookshop.api.ApiModels.LoginRequest;
import com.retailbookshop.api.ApiModels.LoginResponse;
import com.retailbookshop.api.ApiModels.MessageResponse;
import com.retailbookshop.api.ApiModels.PasswordResetRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/password-reset")
    public MessageResponse requestPasswordReset(@RequestBody PasswordResetRequest request) {
        return authService.requestPasswordReset(request);
    }
}
