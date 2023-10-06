package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.SingleOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SingleOperationRepository extends JpaRepository<SingleOperation, Long> {

    @Query("SELECT t FROM SingleOperation t WHERE t.baseOperationId = ?1")
    List<SingleOperation> findExpenses(Long baseOperationId);

    @Modifying
    @Transactional
    @Query("UPDATE SingleOperation t SET t.completed = NOT t.completed where t.id = ?1")
    void updateChecked (Long id);
}
