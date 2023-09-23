package com.example.myfavouritemoney.dto;

public class WalletDTO {

    private String name;
    private String type;
    private String money;

    public WalletDTO(String name, String type, String money) {
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

    public String getMoney() {
        return money;
    }
}
