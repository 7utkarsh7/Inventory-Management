package com.example.inventory.factory;

import com.example.inventory.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductFactoryTest {

    @Test
    public void testCreateProduct() {
        String name = "Book";
        double price = 12.49;
        int initialQuantity = 10;
        String category = "Books";

        Product product = ProductFactory.createProduct(name, price, initialQuantity, category);

        assertEquals(name, product.getName(), "Product name should match.");
        assertEquals(price, product.getPrice(), "Product price should match.");
        assertEquals(initialQuantity, product.getQuantity(), "Product initial quantity should match.");
        assertEquals(category, product.getCategory(), "Product category should match.");
    }
}
