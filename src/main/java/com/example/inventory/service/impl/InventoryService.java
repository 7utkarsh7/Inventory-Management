package com.example.inventory.service.impl;

import java.util.List;
import java.util.Objects;

import com.example.inventory.constants.CommonConstants;
import com.example.inventory.exceptions.InvalidProductQuantityException;
import com.example.inventory.exceptions.ProductNotFoundException;
import com.example.inventory.model.Product;
import com.example.inventory.service.INotificationService;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.IInventoryService;

import static com.example.inventory.constants.CommonConstants.PRODUCT_QUANTITY_THRESHOLD;

public class InventoryService implements IInventoryService {
    private final ProductRepository productRepository;
    private final INotificationService INotificationService;

    public InventoryService(ProductRepository productRepository, INotificationService INotificationService) {
        this.productRepository = productRepository;
        this.INotificationService = INotificationService;
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProductQuantity(String name, int quantity) throws ProductNotFoundException, InvalidProductQuantityException{
        Product product = productRepository.findByName(name);
        if (Objects.isNull(product)) {
            throw new ProductNotFoundException(String.format(CommonConstants.ERROR_PRODUCT_NOT_FOUND, name));
        }
        if (product.getQuantity() + quantity < 0) {
            throw new InvalidProductQuantityException(CommonConstants.ERROR_INVALID_QUANTITY);
        }

        product.updateQuantity(quantity);
            productRepository.save(product);
            checkQuantity(product);

    }

    public void listProducts() {
        productRepository.findAll().forEach(System.out::println);
    }

    public Product searchProductByName(String name) throws ProductNotFoundException {
        Product product = productRepository.findByName(name);
        if (Objects.isNull(product)) {
            throw new ProductNotFoundException(String.format(CommonConstants.ERROR_PRODUCT_NOT_FOUND, name));
        }
        return product;
    }

    public List<Product> searchProductsByCategory(String category) throws ProductNotFoundException {
        List<Product> product = productRepository.findByCategory(category);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(String.format(CommonConstants.ERROR_PRODUCT_NOT_FOUND, category));
        }
        return product;
    }

    private void checkQuantity(Product product) {
        if (product.getQuantity() < CommonConstants.PRODUCT_QUANTITY_THRESHOLD) {
            INotificationService.notifyLowStock(product);
        }
    }
}

