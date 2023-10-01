package com.example.myfavouritemoney.dto;

public class WalletDTO {

    private String name;
    private String type;
    private Float money;

    public WalletDTO(String name, String type, Float money) {
        this.name = name;
        this.type = type;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Float getMoney() {
        return money;
    }
}
