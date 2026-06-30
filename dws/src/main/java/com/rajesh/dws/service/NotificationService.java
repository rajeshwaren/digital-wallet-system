package com.rajesh.dws.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rajesh.dws.entity.Notification;
import com.rajesh.dws.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository
            notificationRepository;

    public void createNotification(
            String email,
            String message) {

        Notification notification =
                new Notification();

        notification.setUserEmail(email);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(
                LocalDateTime.now()
        );

        notificationRepository.save(
                notification
        );
    }

    public List<Notification>
    getNotifications(String email) {

        return notificationRepository
                .findByUserEmailOrderByCreatedAtDesc(
                        email
                );
    }
}