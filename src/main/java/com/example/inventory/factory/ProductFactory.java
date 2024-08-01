package com.example.inventory.factory;

import com.example.inventory.model.Product;

public class ProductFactory {
    public static Product createProduct(String name, double price, int initialQuantity,String category) {
        return new Product(name, price, initialQuantity,category);
    }
}

