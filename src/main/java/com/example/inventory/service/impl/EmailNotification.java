package com.example.inventory.service.impl;

import com.example.inventory.model.Product;
import com.example.inventory.service.INotificationService;

public class EmailNotification implements INotificationService {
    @Override
    public void notifyLowStock(Product product) {
        System.out.println("Sending email: Product " + product.getName() + " is below threshold with quantity " + product.getQuantity());
    }
}

