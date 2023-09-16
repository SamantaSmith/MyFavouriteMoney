package com.example.myfavouritemoney.service;

import com.example.myfavouritemoney.entities.MoneyIncome;
import com.example.myfavouritemoney.repository.MoneyIncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyIncomeService {

    @Autowired
    private MoneyIncomeRepository repository;

    public void addMoneyIncome(MoneyIncome income) {
        repository.save(income);
    }

}
