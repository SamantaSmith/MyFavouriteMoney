package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.SingleOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingleOperationRepository extends JpaRepository<SingleOperation, Long> {

    @Query("SELECT t FROM SingleOperation t WHERE t.baseOperationId = ?1")
    List<SingleOperation> findExpenses(Long baseOperationId);
}
