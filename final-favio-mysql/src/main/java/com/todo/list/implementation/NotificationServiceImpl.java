package com.todo.list.implementation;

import com.todo.list.service.NotificationService;

class NotificationServiceImpl implements NotificationService {
   
    @Override
    public void sendNotification(String message) {
        // Logic for sending email notification
        System.out.println("Email notification sent: " + message);
    }
}