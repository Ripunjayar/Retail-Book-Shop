package com.retailbookshop.payment;

import com.retailbookshop.api.ApiModels.PaymentIntentRequest;
import com.retailbookshop.api.ApiModels.PaymentResponse;
import com.retailbookshop.auth.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final AuthService authService;
    private final PaymentService paymentService;

    public PaymentController(AuthService authService, PaymentService paymentService) {
        this.authService = authService;
        this.paymentService = paymentService;
    }

    @PostMapping("/intent")
    public PaymentResponse createIntent(@RequestHeader(value = "Authorization", required = false) String authorization,
                                        @RequestBody PaymentIntentRequest request) {
        authService.requireUser(authorization);
        return paymentService.createIntent(request);
    }
}
