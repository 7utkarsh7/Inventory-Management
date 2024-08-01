package com.example.inventory.service;

import com.example.inventory.exceptions.InvalidProductQuantityException;
import com.example.inventory.exceptions.ProductNotFoundException;
import com.example.inventory.model.Product;

import java.util.List;

public interface IInventoryService {
    void addProduct(Product product);
    void updateProductQuantity(String name, int quantity) throws ProductNotFoundException, InvalidProductQuantityException;
    void listProducts();
    Product searchProductByName(String name) throws ProductNotFoundException;
    List<Product> searchProductsByCategory(String category) throws ProductNotFoundException;
}
