package com.retailbookshop.order;

import com.retailbookshop.api.ApiModels.CreateOrderRequest;
import com.retailbookshop.api.ApiModels.DeliveryResponse;
import com.retailbookshop.api.ApiModels.OrderResponse;
import com.retailbookshop.auth.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final AuthService authService;
    private final OrderService orderService;

    public OrderController(AuthService authService, OrderService orderService) {
        this.authService = authService;
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse createOrder(@RequestHeader(value = "Authorization", required = false) String authorization,
                                     @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(authService.requireUser(authorization), request);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@RequestHeader(value = "Authorization", required = false) String authorization,
                                  @PathVariable Long orderId) {
        authService.requireUser(authorization);
        return orderService.getOrder(orderId);
    }

    @GetMapping("/{orderId}/delivery")
    public DeliveryResponse getDelivery(@RequestHeader(value = "Authorization", required = false) String authorization,
                                        @PathVariable Long orderId) {
        authService.requireUser(authorization);
        return orderService.getDelivery(orderId);
    }
}
