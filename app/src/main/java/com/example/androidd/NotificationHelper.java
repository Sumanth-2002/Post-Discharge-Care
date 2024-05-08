package com.example.androidd;

import java.util.Random;

public class NotificationHelper {
    // Generate a unique notification ID
    public static int generateNotificationId() {
        // Use a Random object to generate a random number
        Random random = new Random();
        // Return a random integer as the notification ID
        return random.nextInt(9999);
    }
}
