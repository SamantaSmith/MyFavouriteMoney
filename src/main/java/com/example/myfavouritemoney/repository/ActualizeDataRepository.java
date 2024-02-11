package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.ActualizeData;
import com.example.myfavouritemoney.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActualizeDataRepository  extends JpaRepository<ActualizeData, Long> {

    @Query("SELECT t FROM ActualizeData t WHERE t.userId = ?1")
    ActualizeData findByUserId (Long userId);
}
