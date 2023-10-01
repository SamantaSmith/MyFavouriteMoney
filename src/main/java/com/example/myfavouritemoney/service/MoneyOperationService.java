package com.example.myfavouritemoney.service;

import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.entities.MoneyOperation;
import com.example.myfavouritemoney.repository.MoneyOperationRepository;
import com.example.myfavouritemoney.repository.SingleOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoneyOperationService {

    @Autowired
    private MoneyOperationRepository repository;

    @Autowired
    private SingleOperationRepository singleOperationRepository;

    public void addMoneyOperation(MoneyOperation income) {
        repository.save(income);
    }

    public List<MoneyOperationDTO> getExpenses() {

        List<MoneyOperationDTO> list = new ArrayList<>();
        repository.findByUserId(1L).stream().filter(e -> e.getOperationType().equals("EXPENSE")).forEach(e -> {
            singleOperationRepository.findExpenses(e.getId()).forEach(f -> list.add(new MoneyOperationDTO(f.getDate(), f.getCategory(), f.getAmountOfMoney(), f.getCompleted())));
        });
        return list;
    }
}
