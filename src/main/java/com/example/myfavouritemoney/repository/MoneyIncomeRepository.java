package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.MoneyIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyIncomeRepository extends JpaRepository<MoneyIncome, Long> {
}
