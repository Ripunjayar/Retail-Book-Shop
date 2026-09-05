package com.retailbookshop.store;

import com.retailbookshop.api.ApiModels.BookResponse;
import com.retailbookshop.api.ApiModels.CartItemResponse;
import com.retailbookshop.api.ApiModels.OrderResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ShopStore {
    public record User(Long id, String email, String password) { }
    public record CartItem(Long bookId, int quantity) { }
    public record Book(Long id, String title, String author, String category,
                       BigDecimal price, BigDecimal rating, String badge, int stock) { }

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final Map<Long, List<CartItem>> carts = new ConcurrentHashMap<>();
    private final Map<Long, OrderResponse> orders = new ConcurrentHashMap<>();
    private final AtomicLong orderIds = new AtomicLong(1000);

    public ShopStore() {
        users.put(1L, new User(1L, "reader@example.com", "password"));
        addBook(1L, "Atomic Habits", "James Clear", "Self Improvement", "18.99", "4.9", "Bestseller", 12);
        addBook(2L, "The Midnight Library", "Matt Haig", "Fiction", "16.50", "4.8", "Editor's pick", 8);
        addBook(3L, "Deep Work", "Cal Newport", "Business", "20.00", "4.7", "Trending", 5);
        addBook(4L, "The Silent Patient", "Alex Michaelides", "Thriller", "17.75", "4.6", "New release", 4);
        addBook(5L, "The Book of Forest", "M. R. Swan", "Fantasy", "22.00", "4.9", "Limited", 3);
        addBook(6L, "A Brief History of Time", "Stephen Hawking", "Science", "19.25", "4.8", "Classic", 10);
    }

    private void addBook(Long id, String title, String author, String category, String price,
                         String rating, String badge, int stock) {
        books.put(id, new Book(id, title, author, category, new BigDecimal(price),
                new BigDecimal(rating), badge, stock));
    }

    public Map<Long, Book> books() { return books; }
    public Map<Long, List<CartItem>> carts() { return carts; }
    public Map<Long, OrderResponse> orders() { return orders; }
    public User user(Long id) { return users.get(id); }
    public long nextOrderId() { return orderIds.incrementAndGet(); }

    public BookResponse toBookResponse(Book book) {
        return new BookResponse(book.id(), book.title(), book.author(), book.category(),
                book.price(), book.rating(), book.badge(), book.stock());
    }

    public CartItemResponse toCartItemResponse(CartItem item) {
        Book book = books.get(item.bookId());
        return new CartItemResponse(book.id(), book.title(), book.price(), item.quantity());
    }
}
