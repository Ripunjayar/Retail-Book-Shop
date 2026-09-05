package com.retailbookshop.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public final class ApiModels {
    private ApiModels() { }

    public record LoginRequest(String email, String password) { }
    public record LoginResponse(String accessToken, Long userId, String email) { }
    public record PasswordResetRequest(String email) { }
    public record MessageResponse(String message) { }
    public record BookResponse(Long id, String title, String author, String category,
                               BigDecimal price, BigDecimal rating, String badge, int stock) { }
    public record AddCartItemRequest(Long bookId, int quantity) { }
    public record CartItemResponse(Long bookId, String title, BigDecimal unitPrice, int quantity) { }
    public record CartResponse(List<CartItemResponse> items, BigDecimal subtotal, int itemCount) { }
    public record OrderItemRequest(Long bookId, int quantity) { }
    public record CreateOrderRequest(List<OrderItemRequest> items, Long shippingAddressId,
                                     String paymentMethodId) { }
    public record OrderResponse(Long orderId, String status, String paymentStatus,
                                BigDecimal total, LocalDate estimatedDeliveryDate) { }
    public record PaymentIntentRequest(BigDecimal amount, String paymentMethodId) { }
    public record PaymentResponse(String paymentId, String status, BigDecimal amount) { }
    public record DeliveryResponse(Long orderId, LocalDate estimatedDeliveryDate, String status) { }
}
