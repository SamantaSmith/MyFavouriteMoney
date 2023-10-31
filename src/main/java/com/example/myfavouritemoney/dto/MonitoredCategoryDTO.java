package com.example.myfavouritemoney.dto;

import java.time.LocalDate;
import java.util.UUID;

public class MonitoredCategoryDTO {

    private UUID id;
    private LocalDate date;
    private String name;
    private Float currentExpense;
    private Float monthLimit;

    public MonitoredCategoryDTO(UUID id, LocalDate date, String name, Float currentExpense, Float monthLimit) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.currentExpense = currentExpense;
        this.monthLimit = monthLimit;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Float getCurrentExpense() {
        return currentExpense;
    }

    public Float getMonthLimit() {
        return monthLimit;
    }
}
