package com.rajesh.dws.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rajesh.dws.entity.Wallet;
import java.util.Optional;
import com.rajesh.dws.entity.User;
public interface WalletRepository
        extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByUser(User user);
}