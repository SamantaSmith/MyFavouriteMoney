package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("SELECT t FROM Wallet t WHERE t.user_id = ?1")
    List<Wallet> findByUserId (Long userId);
}
