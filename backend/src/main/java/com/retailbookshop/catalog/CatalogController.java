package com.retailbookshop.catalog;

import com.retailbookshop.api.ApiModels.BookResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public List<BookResponse> getBooks(@RequestParam(required = false) String category,
                                      @RequestParam(required = false) String q) {
        return catalogService.findBooks(category, q);
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return catalogService.findBook(id);
    }
}
