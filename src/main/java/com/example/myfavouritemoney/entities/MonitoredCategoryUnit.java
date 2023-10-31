package com.example.myfavouritemoney.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name= "monitored_category_unit")
public class MonitoredCategoryUnit {

    @Id
    private UUID id;
    @Column(name="base_uuid")
    private UUID baseUUID;
    @Column(name="month")
    private LocalDate month;
    @Column(name="month_limit")
    private Float monthLimit;
    @Column(name="current_expense")
    private Float currentExpense;
    @Column(name="name")
    private String name;

    public MonitoredCategoryUnit() {
    }

    public MonitoredCategoryUnit(UUID id, UUID baseUUID, LocalDate month, Float monthLimit, Float currentExpense, String name) {
        this.id = id;
        this.baseUUID = baseUUID;
        this.month = month;
        this.monthLimit = monthLimit;
        this.currentExpense = currentExpense;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBaseUUID() {
        return baseUUID;
    }

    public LocalDate getMonth() {
        return month;
    }

    public Float getMonthLimit() {
        return monthLimit;
    }

    public Float getCurrentExpense() {
        return currentExpense;
    }

    public String getName() {
        return name;
    }
}
