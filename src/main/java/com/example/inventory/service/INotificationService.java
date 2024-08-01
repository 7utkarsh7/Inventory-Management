package com.example.inventory.service;

import com.example.inventory.model.Product;

public interface INotificationService {
    void notifyLowStock(Product product);
}

