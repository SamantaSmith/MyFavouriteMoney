package com.example.myfavouritemoney.controller;

import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.entities.MoneyOperation;
import com.example.myfavouritemoney.entities.SingleOperation;
import com.example.myfavouritemoney.enums.OperationRegularity;
import com.example.myfavouritemoney.enums.OperationType;
import com.example.myfavouritemoney.service.MoneyOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping(path = "moneyOperation")
public class MoneyOperationController {

    @Autowired
    private MoneyOperationService service;

    @GetMapping(path = "/getExpenses")
    public List<MoneyOperationDTO> getExpenses(int year, int month) {
        return service.getExpensesByMonth(year, month);
    }
    public void saveSingleExpense(Map<String, Object> map) {
        System.out.println(map);

        var uuid = UUID.randomUUID();
        var singleUUID = UUID.randomUUID();

        var a = new MoneyOperation(
                uuid,
                1L,
                OperationType.EXPENSE.name(),
                map.get("regularity").equals("Одиночный") ? OperationRegularity.SINGLE.name() : OperationRegularity.REGULAR.name(),
                singleUUID,
                null,
                true
                );

        var b = new SingleOperation(
                singleUUID,
                1L,
                uuid,
                (Float) map.get("money"),
                (LocalDate) map.get("date"),
                false,
                (String) map.get("category")
        );

        service.saveSingle(a, b);
    }

    public void updateChecked(UUID id, OperationRegularity regularity) {
        service.updateChecked(id, regularity);
    }


}
