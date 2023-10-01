package com.example.myfavouritemoney.dto;

import java.util.Date;

public class MoneyOperationDTO {

    private Date date;
    private String category;
    private Float money;
    private Boolean completed;

    public MoneyOperationDTO(Date date, String category, Float money, Boolean completed) {
        this.date = date;
        this.category = category;
        this.money = money;
        this.completed = completed;
    }

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public Float getMoney() {
        return money;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getChecked() {
        return this.completed ? "checked" : "";
    }
}
