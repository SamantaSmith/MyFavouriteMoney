package com.example.myfavouritemoney.controller;

import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.enums.OperationRegularity;
import com.example.myfavouritemoney.service.MoneyOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "moneyOperation")
public class MoneyOperationController {

    @Autowired
    private MoneyOperationService service;

    @GetMapping(path = "/getExpenses")
    public List<MoneyOperationDTO> getExpenses(int year, int month) {
        return service.getExpensesByMonth(year, month);
    }

    public void updateChecked(UUID id, OperationRegularity regularity) {
        service.updateChecked(id, regularity);
    }


}
