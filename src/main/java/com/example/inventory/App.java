package com.example.inventory;

import com.example.inventory.exceptions.InvalidProductQuantityException;
import com.example.inventory.exceptions.ProductNotFoundException;
import com.example.inventory.model.Product;
import com.example.inventory.service.INotificationService;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.IInventoryService;
import com.example.inventory.service.impl.InventoryService;
import com.example.inventory.service.impl.EmailNotification;
import com.example.inventory.factory.ProductFactory;

public class App {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        INotificationService emailNotification = new EmailNotification();
        IInventoryService inventoryService = new InventoryService(productRepository, emailNotification);

        try {
            Product book = ProductFactory.createProduct("Book", 12.49, 10, "Books");
            Product musicCD = ProductFactory.createProduct("Music CD", 14.99, 3, "Music");
            Product chocolateBar = ProductFactory.createProduct("Chocolate Bar", 0.85, 20, "Food");

            inventoryService.addProduct(book);
            inventoryService.addProduct(musicCD);
            inventoryService.addProduct(chocolateBar);

            inventoryService.updateProductQuantity("Music CD", 2);

            System.out.println("List of All Products:");
            inventoryService.listProducts();

            Product searchedByName = inventoryService.searchProductByName("book");
            System.out.println("\nSearch Results by Name ('book'):");
            if (searchedByName != null) {
                System.out.println(searchedByName);
            } else {
                System.out.println("No product found by that name.");
            }

            System.out.println("\nSearch Results by Category ('Food'):");
            java.util.List<Product> searchedByCategory = inventoryService.searchProductsByCategory("Food");
            if (!searchedByCategory.isEmpty()) {
                searchedByCategory.forEach(System.out::println);
            } else {
                System.out.println("No products found in that category.");
            }

        } catch (ProductNotFoundException | InvalidProductQuantityException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
