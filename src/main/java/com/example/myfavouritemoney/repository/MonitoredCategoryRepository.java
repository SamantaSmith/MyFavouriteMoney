package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.MoneyOperation;
import com.example.myfavouritemoney.entities.MonitoredCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoredCategoryRepository extends JpaRepository<MonitoredCategory, Long> {
}
