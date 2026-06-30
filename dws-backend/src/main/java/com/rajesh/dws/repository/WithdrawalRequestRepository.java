package com.rajesh.dws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajesh.dws.entity.WithdrawalRequest;

public interface WithdrawalRequestRepository
        extends JpaRepository<WithdrawalRequest, Long> {

    List<WithdrawalRequest>
    findByUserEmail(String email);
}