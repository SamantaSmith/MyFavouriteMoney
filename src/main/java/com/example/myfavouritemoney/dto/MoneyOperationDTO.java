package com.example.myfavouritemoney.dto;

import com.example.myfavouritemoney.enums.OperationRegularity;
import com.vaadin.flow.component.Component;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class MoneyOperationDTO {

    private UUID id;
    private LocalDate date;
    private String category;
    private Float money;
    private Boolean completed;
    private OperationRegularity regularity;

    public MoneyOperationDTO(UUID id, LocalDate date, String category, Float money, Boolean completed, OperationRegularity regularity) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.money = money;
        this.completed = completed;
        this.regularity = regularity;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getMoney() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(money)+" ₽";
    }

    public Boolean getCompletedBoolean() {
        return completed;
    }

    public String getCompleted() {
        return completed ? "checked" : "";
    }

    public OperationRegularity getRegularity() {
        return regularity;
    }

    public String getTranslatedRegularity() {
        switch (regularity) {
            case REGULAR -> {
                return "Регулярный";
            }
            case SINGLE -> {
                return "Одиночный";
            }
            default -> {
              return regularity.name();
            }
        }
    }
}
