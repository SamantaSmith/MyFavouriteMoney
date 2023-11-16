package com.example.myfavouritemoney.controller;

import com.example.myfavouritemoney.dto.MonitoredCategoryDTO;
import com.example.myfavouritemoney.entities.MonitoredCategory;
import com.example.myfavouritemoney.entities.MonitoredCategoryUnit;
import com.example.myfavouritemoney.service.MonitoredCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class MonitoredCategoryController {

    @Autowired
    private MonitoredCategoryService service;

    public List<MonitoredCategoryDTO> getCategories(int year, int month) {
        return service.getCategories(year, month);
    }
    public LocalDate saveCategory(Map<String, Object> map) {
        System.out.println(map);

        var baseUUID = UUID.randomUUID();
        var unitUUID = UUID.randomUUID();

        var base = new MonitoredCategory(
                baseUUID,
                (String) map.get("category"),
                1L
        );

        var unit = new MonitoredCategoryUnit(
                unitUUID,
                baseUUID,
                LocalDate.of((int) map.get("yearPicker"), (Month) map.get("monthPicker"), 1),
                (Float) map.get("limitMoney"),
                0f,
                (String) map.get("category")
        );

        service.saveCategory(base, unit);

        return LocalDate.of((int) map.get("yearPicker"), (Month) map.get("monthPicker"), 1);
    }

    public void addExpense(UUID id, Float money) {
        service.addExpense(id, money);
    }
}
