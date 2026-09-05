package com.retailbookshop.auth;

import com.retailbookshop.api.ApiModels.LoginRequest;
import com.retailbookshop.api.ApiModels.LoginResponse;
import com.retailbookshop.api.ApiModels.MessageResponse;
import com.retailbookshop.api.ApiModels.PasswordResetRequest;
import com.retailbookshop.store.ShopStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final ShopStore store;

    public AuthService(ShopStore store) {
        this.store = store;
    }

    public LoginResponse login(LoginRequest request) {
        ShopStore.User user = store.user(1L);
        if (request == null || !user.email().equals(request.email()) || !user.password().equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return new LoginResponse("demo-token-user-1", user.id(), user.email());
    }

    public Long requireUser(String authorization) {
        if (!"Bearer demo-token-user-1".equals(authorization)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid access token");
        }
        return 1L;
    }

    public MessageResponse requestPasswordReset(PasswordResetRequest request) {
        if (request == null || request.email() == null || request.email().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        return new MessageResponse("If the account exists, a password reset link will be sent");
    }
}
