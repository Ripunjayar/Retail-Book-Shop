package com.retailbookshop.cart;

import com.retailbookshop.api.ApiModels.AddCartItemRequest;
import com.retailbookshop.api.ApiModels.CartResponse;
import com.retailbookshop.store.ShopStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final ShopStore store;

    public CartService(ShopStore store) {
        this.store = store;
    }

    public CartResponse getCart(Long userId) {
        List<ShopStore.CartItem> items = store.carts().getOrDefault(userId, new ArrayList<>());
        var responseItems = items.stream().map(store::toCartItemResponse).toList();
        BigDecimal subtotal = responseItems.stream()
                .map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int itemCount = responseItems.stream().mapToInt(item -> item.quantity()).sum();
        return new CartResponse(responseItems, subtotal, itemCount);
    }

    public CartResponse addItem(Long userId, AddCartItemRequest request) {
        if (request == null || request.bookId() == null || request.quantity() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bookId and positive quantity are required");
        }
        ShopStore.Book book = store.books().get(request.bookId());
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        if (request.quantity() > book.stock()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient stock");
        }
        List<ShopStore.CartItem> items = store.carts().computeIfAbsent(userId, ignored -> new ArrayList<>());
        for (int index = 0; index < items.size(); index++) {
            ShopStore.CartItem item = items.get(index);
            if (item.bookId().equals(request.bookId())) {
                items.set(index, new ShopStore.CartItem(item.bookId(), item.quantity() + request.quantity()));
                return getCart(userId);
            }
        }
        items.add(new ShopStore.CartItem(request.bookId(), request.quantity()));
        return getCart(userId);
    }

    public CartResponse removeItem(Long userId, Long bookId) {
        List<ShopStore.CartItem> items = store.carts().getOrDefault(userId, new ArrayList<>());
        items.removeIf(item -> item.bookId().equals(bookId));
        return getCart(userId);
    }
}
