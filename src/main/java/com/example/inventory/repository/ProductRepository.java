package com.example.inventory.repository;

import com.example.inventory.model.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;

public class ProductRepository {
    private final Map<String, Product> productMap = new HashMap<>();
    private final Map<String, List<Product>> categoryMap = new HashMap<>();

    public void save(Product product) {
        productMap.put(product.getName().toLowerCase(), product);

        categoryMap.computeIfAbsent(product.getCategory().toLowerCase(), k -> new ArrayList<>())
                   .removeIf(p -> p.getName().equalsIgnoreCase(product.getName()));
        categoryMap.computeIfAbsent(product.getCategory().toLowerCase(), k -> new ArrayList<>()).add(product);
    }

    public Product findByName(String name) {
        return productMap.get(name.toLowerCase());
    }

    public Collection<Product> findAll() {
        return productMap.values();
    }

    public List<Product> findByCategory(String category) {
        return categoryMap.getOrDefault(category.toLowerCase(), new ArrayList<>());
    }

    public void delete(String name) {
        Product product = productMap.remove(name.toLowerCase());
        if (product != null) {
            List<Product> productsInCategory = categoryMap.get(product.getCategory().toLowerCase());
            if (productsInCategory != null) {
                productsInCategory.removeIf(p -> p.getName().equalsIgnoreCase(name));
                if (productsInCategory.isEmpty()) {
                    categoryMap.remove(product.getCategory().toLowerCase());
                }
            }
        }
    }

    public boolean exists(String name) {
        return productMap.containsKey(name.toLowerCase());
    }
}
