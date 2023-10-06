package com.example.myfavouritemoney.dto;

import java.util.Date;

public class MoneyOperationDTO {

    private Long id;
    private Date date;
    private String category;
    private Float money;
    private Boolean completed;

    public MoneyOperationDTO(Long id, Date date, String category, Float money, Boolean completed) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.money = money;
        this.completed = completed;
    }

    public Long getId() {
        return id;
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

    public String getCompleted() {
        return completed ? "checked" : "";
    }
}
