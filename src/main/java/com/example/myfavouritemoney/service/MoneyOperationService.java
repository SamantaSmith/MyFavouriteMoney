package com.example.myfavouritemoney.service;

import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.entities.MoneyOperation;
import com.example.myfavouritemoney.entities.SingleOperation;
import com.example.myfavouritemoney.enums.OperationRegularity;
import com.example.myfavouritemoney.enums.OperationType;
import com.example.myfavouritemoney.repository.MoneyOperationRepository;
import com.example.myfavouritemoney.repository.RegularOperationRepository;
import com.example.myfavouritemoney.repository.RegularOperationUnitRepository;
import com.example.myfavouritemoney.repository.SingleOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MoneyOperationService {
    @Autowired
    private MoneyOperationRepository repository;
    @Autowired
    private SingleOperationRepository singleOperationRepository;
    @Autowired
    private RegularOperationRepository regularOperationRepository;
    @Autowired
    private RegularOperationUnitRepository regularOperationUnitRepository;

    public void saveSingle(MoneyOperation base, SingleOperation singleOperation) {
        repository.save(base);
        singleOperationRepository.save(singleOperation);
    }

    public List<MoneyOperationDTO> getExpensesByMonth(int year, int month) {

        List<MoneyOperationDTO> response = new ArrayList<>();
        var stringMonth = month >= 10 ? String.valueOf(month) : '0' + String.valueOf(month);

        response.addAll(singleOperationRepository
                .findSingleExpenses(String.valueOf(year), stringMonth, OperationType.EXPENSE.name())
                .stream()
                .map(e -> new MoneyOperationDTO(e.getId(), e.getDate(), e.getCategory(), e.getAmountOfMoney(), e.getCompleted(), OperationRegularity.SINGLE))
                .toList());

        response.addAll(regularOperationUnitRepository
                .findRegularExpenses(String.valueOf(year), stringMonth, OperationType.EXPENSE.name())
                .stream()
                .map(e -> new MoneyOperationDTO(e.getId(), e.getUnitOperationDate(), e.getCategory(), e.getRealAmountOfMoney(), e.getCompleted(), OperationRegularity.REGULAR))
                .toList());

        response.sort(new MoneyOperationDTOExpenseComparator());
        return response;
    }

    public void updateChecked (UUID id, OperationRegularity regularity) {
        if (regularity == OperationRegularity.SINGLE) {
            singleOperationRepository.updateChecked(id);
        } else {
            regularOperationUnitRepository.updateChecked(id);
        }
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