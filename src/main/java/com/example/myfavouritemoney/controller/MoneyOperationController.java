package com.example.myfavouritemoney.controller;

import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.entities.MoneyOperation;
import com.example.myfavouritemoney.entities.RegularOperation;
import com.example.myfavouritemoney.entities.RegularOperationUnit;
import com.example.myfavouritemoney.entities.SingleOperation;
import com.example.myfavouritemoney.enums.OperationRegularity;
import com.example.myfavouritemoney.enums.OperationType;
import com.example.myfavouritemoney.service.MoneyOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
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
    public LocalDate saveExpense(Map<String, Object> map) {
        System.out.println(map);

        var regularity = map.get("regularity").equals("Одиночный") ? OperationRegularity.SINGLE : OperationRegularity.REGULAR;

        var uuid = UUID.randomUUID();

        if (regularity == OperationRegularity.SINGLE) {
            var singleUUID = UUID.randomUUID();
            var moneyOperation = new MoneyOperation(
                    uuid,
                    1L,
                    OperationType.EXPENSE.name(),
                    map.get("regularity").equals("Одиночный") ? OperationRegularity.SINGLE.name() : OperationRegularity.REGULAR.name(),
                    singleUUID,
                    null,
                    true
            );
            var singleOperation = new SingleOperation(
                    singleUUID,
                    1L,
                    uuid,
                    (Float) map.get("money"),
                    (LocalDate) map.get("date"),
                    false,
                    (String) map.get("category")
            );
            service.saveSingle(moneyOperation, singleOperation);
        } else {
            var regularUUID = UUID.randomUUID();
            var regularUnitUUID = UUID.randomUUID();
            var moneyOperation = new MoneyOperation(
                    uuid,
                    1L,
                    OperationType.EXPENSE.name(),
                    map.get("regularity").equals("Одиночный") ? OperationRegularity.SINGLE.name() : OperationRegularity.REGULAR.name(),
                    null,
                    regularUUID,
                    true
            );
            var regularOperation = new RegularOperation(
                    regularUUID,
                    uuid,
                    "MONTH",
                    new int[] {((Float) map.get("payday")).intValue()},
                    (Float) map.get("standartMoney"),
                    (String) map.get("category")
            );
            var regularOperationUnit = new RegularOperationUnit(
                    regularUnitUUID,
                    regularUUID,
                    (Float) map.get("standartMoney"),
                    LocalDate.of((int) map.get("yearPicker"), (Month) map.get("monthPicker"), ((Float) map.get("payday")).intValue()),
                    false,
                    (String) map.get("category")

            );
            service.saveRegular(moneyOperation, regularOperation, regularOperationUnit);
        }

        if (map.get("regularity").equals("Одиночный")) {
            return (LocalDate) map.get("date");
        } else {
            return LocalDate.now();
        }
    }
    public void deleteExpense(UUID id, OperationRegularity regularity) {
        service.deleteExpense(id, regularity);
    }
    public void updateChecked(UUID id, OperationRegularity regularity) {
        service.updateChecked(id, regularity);
    }
    public void updateSingle(UUID singleUUID, String category, LocalDate date, Double money) {
        service.updateSingle(singleUUID, category, date, money.floatValue());
    }
    public void updateRegularUnit(UUID id, LocalDate date, Double money) {
        service.updateRegularUnit(id, date, money.floatValue());
    }
}
