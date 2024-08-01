package com.example.inventory.model;

public class Product {
    private final String name;
    private final double price;
    private int quantity;
    private final String category;

    public Product(String name, double price, int initialQuantity,String category) {
        this.name = name;
        this.price = price;
        this.quantity = initialQuantity;
        this.category=category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateQuantity(int amount) {
        this.quantity += amount;
    }

    @Override
    public String toString() {
        return  name + " ( "+ category +" ): $ " + price + ", Quantity :" + quantity;
    }
}

