package com.retailbookshop.payment;

import com.retailbookshop.api.ApiModels.PaymentIntentRequest;
import com.retailbookshop.api.ApiModels.PaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class PaymentService {
    public PaymentResponse createIntent(PaymentIntentRequest request) {
        if (request == null || request.amount() == null || request.amount().signum() <= 0
                || request.paymentMethodId() == null || request.paymentMethodId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A positive amount and payment method are required");
        }
        // Demo response. Production code should call Stripe/Adyen and never store raw card details.
        return new PaymentResponse("pay_" + UUID.randomUUID(), "PAID", request.amount());
    }
}
