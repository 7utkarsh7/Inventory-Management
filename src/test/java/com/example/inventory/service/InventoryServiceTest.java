package com.example.inventory.service;

import com.example.inventory.exceptions.InvalidProductQuantityException;
import com.example.inventory.exceptions.ProductNotFoundException;
import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.impl.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private INotificationService INotificationService;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product("Book", 12.49, 10, "Books");

        inventoryService.addProduct(product);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProductQuantitySuccess() throws ProductNotFoundException, InvalidProductQuantityException {
        Product product = new Product("Music CD", 14.99, 3, "Music");
        when(productRepository.findByName("Music CD")).thenReturn(product);

        inventoryService.updateProductQuantity("Music CD", 2);

        assertEquals(5, product.getQuantity());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProductQuantityNegative() throws ProductNotFoundException, InvalidProductQuantityException {
        Product product = new Product("Music CD", 14.99, 3, "Music");
        when(productRepository.findByName("Music CD")).thenReturn(product);

        assertThrows(InvalidProductQuantityException.class,() -> inventoryService.updateProductQuantity("Music CD", -4));
    }

    @Test
    public void testUpdateProductQuantityNull() throws ProductNotFoundException {

        assertThrows(ProductNotFoundException.class,() -> inventoryService.updateProductQuantity("Music CD", -4));
    }

    @Test
    public void testUpdateProductQuantityProductNotFound() {
        when(productRepository.findByName("NonExistent Product")).thenReturn(null);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                inventoryService.updateProductQuantity("NonExistent Product", 2)
        );
        assertEquals("Product with name NonExistent Product not found.", exception.getMessage());
    }

    @Test
    public void testUpdateProductQuantityInvalidQuantity() throws ProductNotFoundException {
        Product product = new Product("Music CD", 14.99, 3, "Music");
        when(productRepository.findByName("Music CD")).thenReturn(product);

        InvalidProductQuantityException exception = assertThrows(InvalidProductQuantityException.class, () ->
                inventoryService.updateProductQuantity("Music CD", -10)
        );
        assertEquals("Quantity cannot be negative after update.", exception.getMessage());
    }

    @Test
    public void testListProducts() {
        Product book = new Product("Book", 12.49, 10, "Books");
        Product musicCD = new Product("Music CD", 14.99, 3, "Music");
        when(productRepository.findAll()).thenReturn(Arrays.asList(book, musicCD));

        inventoryService.listProducts();

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testSearchProductByNameSuccess() throws ProductNotFoundException {
        Product product = new Product("Book", 12.49, 10, "Books");
        when(productRepository.findByName("Book")).thenReturn(product);

        Product foundProduct = inventoryService.searchProductByName("Book");

        assertEquals(product, foundProduct);
    }

    @Test
    public void testSearchProductByNameProductNotFound() {
        when(productRepository.findByName("NonExistent Product")).thenReturn(null);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                inventoryService.searchProductByName("NonExistent Product")
        );
        assertEquals("Product with name NonExistent Product not found.", exception.getMessage());
    }

    @Test
    public void testSearchProductsByCategory() throws ProductNotFoundException {
        Product chocolateBar = new Product("Chocolate Bar", 0.85, 20, "Food");
        when(productRepository.findByCategory("Food")).thenReturn(Collections.singletonList(chocolateBar));

        List<Product> products = inventoryService.searchProductsByCategory("Food");

        assertEquals(1, products.size());
        assertEquals(chocolateBar, products.get(0));
    }

    @Test
    public void testSearchProductByCategoryProductNotFound() {
        when(productRepository.findByCategory("NonExistent Product")).thenReturn(null);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                inventoryService.searchProductByName("NonExistent Product")
        );
        assertEquals("Product with name NonExistent Product not found.", exception.getMessage());
    }
}
