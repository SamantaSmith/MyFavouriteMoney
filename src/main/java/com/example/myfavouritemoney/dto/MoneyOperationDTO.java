package com.example.myfavouritemoney.dto;

import com.example.myfavouritemoney.enums.OperationRegularity;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

public class MoneyOperationDTO {

    private UUID id;
    private Date date;
    private String category;
    private Float money;
    private Boolean completed;
    private OperationRegularity regularity;

    public MoneyOperationDTO(UUID id, Date date, String category, Float money, Boolean completed, OperationRegularity regularity) {
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

    public Date getDate() {
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
}
