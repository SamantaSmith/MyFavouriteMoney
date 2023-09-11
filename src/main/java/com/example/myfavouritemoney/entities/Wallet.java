package com.example.myfavouritemoney.entities;

import jakarta.persistence.*;

@Entity
@Table(name="wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wallet_id;
    @Column(name="name")
    private String name;
    @Column(name="user_id")
    private Long user_id;
    @Column(name="type")
    private Integer type;
    @Column(name="money")
    private Float money;

    public Wallet(String name, Long user_id, Integer type, Float money) {
        this.name = name;
        this.user_id = user_id;
        this.type = type;
        this.money = money;
    }

    public Wallet(Long wallet_id, String name, Long user_id, Integer type, Float money) {
        this.wallet_id = wallet_id;
        this.name = name;
        this.user_id = user_id;
        this.type = type;
        this.money = money;
    }

    public Wallet() {

    }

    public Long getWallet_id() {
        return wallet_id;
    }

    public String getName() {
        return name;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Integer getType() {
        return type;
    }

    public Float getMoney() {
        return money;
    }


}
