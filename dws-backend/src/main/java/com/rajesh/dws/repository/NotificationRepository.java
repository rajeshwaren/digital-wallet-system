package com.rajesh.dws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajesh.dws.entity.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification>
    findByUserEmailOrderByCreatedAtDesc(
            String email
    );
}