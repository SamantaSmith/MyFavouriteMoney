package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.MoneyOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface MoneyOperationRepository extends JpaRepository<MoneyOperation, Long> {

    @Query("SELECT t FROM MoneyOperation t WHERE t.userId = ?1")
    List<MoneyOperation> findByUserIdAndMonth(Long userId, int month);
    @Modifying
    @Transactional
    @Query("UPDATE MoneyOperation rou SET rou.actual = false where rou.id = ?1")
    void setInactive(UUID id);
}
