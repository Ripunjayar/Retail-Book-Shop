package com.retailbookshop.catalog;

import com.retailbookshop.api.ApiModels.BookResponse;
import com.retailbookshop.store.ShopStore;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CatalogService {
    private final ShopStore store;

    public CatalogService(ShopStore store) {
        this.store = store;
    }

        public List<BookResponse> findBooks(String category, String search) {
        return store.books().values().stream()
                .filter(book -> category == null || category.isBlank() || "All".equalsIgnoreCase(category)
                        || book.category().equalsIgnoreCase(category))
            .filter(book -> search == null || search.isBlank()
                || book.title().toLowerCase().contains(search.toLowerCase())
                || book.author().toLowerCase().contains(search.toLowerCase()))
                .map(store::toBookResponse)
                .toList();
    }

    public BookResponse findBook(Long id) {
        ShopStore.Book book = store.books().get(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found: " + id);
        }
        return store.toBookResponse(book);
    }
}
