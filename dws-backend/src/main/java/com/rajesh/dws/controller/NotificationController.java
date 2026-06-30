package com.rajesh.dws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.rajesh.dws.entity.Notification;
import com.rajesh.dws.service.NotificationService;

@RestController
@RequestMapping("/api/user")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    public List<Notification>
    notifications(
            Authentication auth) {

        return notificationService
                .getNotifications(
                        auth.getName()
                );
    }
}