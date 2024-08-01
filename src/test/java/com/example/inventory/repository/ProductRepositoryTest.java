package com.example.inventory.repository;

import com.example.inventory.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    private ProductRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new ProductRepository();
    }

    @Test
    public void testSaveAndFindProduct() {
        
        Product product = new Product("Book", 12.49, 10, "Books");

        repository.save(product);
        Product foundProduct = repository.findByName("Book");

        assertNotNull(foundProduct, "Product should be found.");
        assertEquals("Book", foundProduct.getName(), "Product name should match.");
        assertEquals(12.49, foundProduct.getPrice(), "Product price should match.");
    }

    @Test
    public void testSaveAndFindByName() {
        Product product = new Product("Book", 12.49, 10, "Books");
        repository.save(product);

        Product foundProduct = repository.findByName("book"); // case insensitive
        assertNotNull(foundProduct);
        assertEquals("Book", foundProduct.getName());
        assertEquals(12.49, foundProduct.getPrice());
        assertEquals(10, foundProduct.getQuantity());
        assertEquals("Books", foundProduct.getCategory());
    }

    @Test
    public void testFindAllProducts() {
        
        Product product1 = new Product("Book", 12.49, 10, "Books");
        Product product2 = new Product("Music CD", 14.99, 5, "Music");
        repository.save(product1);
        repository.save(product2);

        Collection<Product> allProductsCollection = repository.findAll();
        List<Product> allProducts = new ArrayList<>(allProductsCollection);

        assertEquals(2, allProducts.size(), "There should be two products.");
    }

    @Test
    public void testFindByCategory() {
        
        Product product1 = new Product("Book", 12.49, 10, "Books");
        Product product2 = new Product("Chocolate Bar", 0.85, 20, "Food");
        repository.save(product1);
        repository.save(product2);

        List<Product> books = repository.findByCategory("Books");
        List<Product> food = repository.findByCategory("Food");

        assertEquals(1, books.size(), "There should be one product in the 'Books' category.");
        assertEquals("Book", books.get(0).getName(), "The product in 'Books' category should be 'Book'.");

        assertEquals(1, food.size(), "There should be one product in the 'Food' category.");
        assertEquals("Chocolate Bar", food.get(0).getName(), "The product in 'Food' category should be 'Chocolate Bar'.");
    }

    @Test
    public void testDeleteProduct() {
        
        Product product = new Product("Book", 12.49, 10, "Books");
        repository.save(product);

        repository.delete("Book");
        Product deletedProduct = repository.findByName("Book");

        assertNull(deletedProduct, "Product should be deleted.");
    }

    @Test
    public void testDeleteProduct_RemovesFromCategory() {
        Product product = new Product("Chocolate Bar", 0.85, 20, "Food");
        repository.save(product);
        repository.delete("chocolate bar"); // case insensitive

        List<Product> products = repository.findByCategory("food"); // case insensitive
        assertTrue(products.isEmpty());
    }

    @Test
    public void testExists() {
        
        Product product = new Product("Book", 12.49, 10, "Books");
        repository.save(product);

        assertTrue(repository.exists("Book"), "Product should exist.");
        assertFalse(repository.exists("Nonexistent Product"), "Nonexistent product should not exist.");
    }
}
