package com.example.myfavouritemoney.service;

import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.entities.MoneyOperation;
import com.example.myfavouritemoney.repository.MoneyOperationRepository;
import com.example.myfavouritemoney.repository.SingleOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    public List<MoneyOperationDTO> getExpensesByMonth(int year, int month) {

        return singleOperationRepository
                .findExpenses(String.valueOf(year), month >= 10 ? String.valueOf(month) : '0' + String.valueOf(month))
                .stream()
                .map(e -> new MoneyOperationDTO(e.getId(), e.getDate(), e.getCategory(), e.getAmountOfMoney(), e.getCompleted()))
                .sorted(new MoneyOperationDTOExpenseComparator())
                .collect(Collectors.toList());
    }

    public void updateChecked (Long id) {
        singleOperationRepository.updateChecked(id);
    }

    static class MoneyOperationDTOExpenseComparator implements Comparator<MoneyOperationDTO> {
        @Override
        public int compare(MoneyOperationDTO o1, MoneyOperationDTO o2) {
            return o1.getCompletedBoolean().compareTo(o2.getCompletedBoolean()) != 0
                    ? o1.getCompletedBoolean().compareTo(o2.getCompletedBoolean())
                    : o1.getDate().compareTo(o2.getDate());
        }
    }
}