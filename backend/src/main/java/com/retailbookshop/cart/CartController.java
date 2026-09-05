package com.retailbookshop.cart;

import com.retailbookshop.api.ApiModels.AddCartItemRequest;
import com.retailbookshop.api.ApiModels.CartResponse;
import com.retailbookshop.auth.AuthService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final AuthService authService;
    private final CartService cartService;

    public CartController(AuthService authService, CartService cartService) {
        this.authService = authService;
        this.cartService = cartService;
    }

    @GetMapping
    public CartResponse getCart(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return cartService.getCart(authService.requireUser(authorization));
    }

    @PostMapping("/items")
    public CartResponse addItem(@RequestHeader(value = "Authorization", required = false) String authorization,
                                @RequestBody AddCartItemRequest request) {
        return cartService.addItem(authService.requireUser(authorization), request);
    }

    @DeleteMapping("/items/{bookId}")
    public CartResponse removeItem(@RequestHeader(value = "Authorization", required = false) String authorization,
                                   @PathVariable Long bookId) {
        return cartService.removeItem(authService.requireUser(authorization), bookId);
    }
}
