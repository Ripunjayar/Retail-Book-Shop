package com.retailbookshop.order;

import com.retailbookshop.api.ApiModels.CreateOrderRequest;
import com.retailbookshop.api.ApiModels.DeliveryResponse;
import com.retailbookshop.api.ApiModels.OrderItemRequest;
import com.retailbookshop.api.ApiModels.OrderResponse;
import com.retailbookshop.api.ApiModels.PaymentIntentRequest;
import com.retailbookshop.payment.PaymentService;
import com.retailbookshop.store.ShopStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class OrderService {
    private final ShopStore store;
    private final PaymentService paymentService;

    public OrderService(ShopStore store, PaymentService paymentService) {
        this.store = store;
        this.paymentService = paymentService;
    }

    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
        if (request == null || request.items() == null || request.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one order item is required");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest item : request.items()) {
            ShopStore.Book book = store.books().get(item.bookId());
            if (book == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found: " + item.bookId());
            }
            if (item.quantity() < 1 || item.quantity() > book.stock()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient stock for: " + book.title());
            }
            total = total.add(book.price().multiply(BigDecimal.valueOf(item.quantity())));
        }

        var payment = paymentService.createIntent(new PaymentIntentRequest(total, request.paymentMethodId()));
        if (!"PAID".equals(payment.status())) {
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "Payment was not completed");
        }

        for (OrderItemRequest item : request.items()) {
            ShopStore.Book book = store.books().get(item.bookId());
            store.books().put(item.bookId(), new ShopStore.Book(book.id(), book.title(), book.author(), book.category(),
                    book.price(), book.rating(), book.badge(), book.stock() - item.quantity()));
        }
        store.carts().remove(userId);
        Long orderId = store.nextOrderId();
        OrderResponse order = new OrderResponse(orderId, "CONFIRMED", payment.status(), total,
                LocalDate.now().plusDays(5));
        store.orders().put(orderId, order);
        return order;
    }

    public OrderResponse getOrder(Long orderId) {
        OrderResponse order = store.orders().get(orderId);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return order;
    }

    public DeliveryResponse getDelivery(Long orderId) {
        OrderResponse order = getOrder(orderId);
        return new DeliveryResponse(order.orderId(), order.estimatedDeliveryDate(), "IN_TRANSIT");
    }
}
