package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.MoneyOperation;
import com.example.myfavouritemoney.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoneyOperationRepository extends JpaRepository<MoneyOperation, Long> {

    @Query("SELECT t FROM MoneyOperation t WHERE t.userId = ?1")
    List<MoneyOperation> findByUserId (Long userId);
}
