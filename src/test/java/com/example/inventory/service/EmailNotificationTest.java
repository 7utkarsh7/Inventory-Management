package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.example.inventory.service.impl.EmailNotification;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailNotificationTest {

    @Test
    public void testNotifyLowStock() {
        EmailNotification emailNotification = new EmailNotification();
        Product product = new Product("Chocolate Bar", 0.85, 5, "Food");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        emailNotification.notifyLowStock(product);

        System.setOut(originalOut);

        String expectedOutput = "Sending email: Product Chocolate Bar is below threshold with quantity 5\n";
        assertEquals(expectedOutput, outputStream.toString(), "The output message should match.");
    }
}
