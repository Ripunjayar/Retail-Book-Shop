package com.retailbookshop.inventory;

import com.retailbookshop.api.ApiModels.BookResponse;
import com.retailbookshop.catalog.CatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final CatalogService catalogService;

    public InventoryController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{bookId}")
    public BookResponse getAvailability(@PathVariable Long bookId) {
        return catalogService.findBook(bookId);
    }
}
