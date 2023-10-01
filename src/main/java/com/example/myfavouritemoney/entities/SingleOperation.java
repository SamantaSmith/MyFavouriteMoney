package com.example.myfavouritemoney.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "single_operation")
public class SingleOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="base_operation_id")
    private Long baseOperationId;
    @Column(name="wallet_id")
    private Long walletId;
    @Column(name="amount_of_money")
    private Float amountOfMoney;
    @Column(name="date")
    private Date date;
    @Column(name="completed")
    private Boolean completed;
    @Column(name="category")
    private String category;

    public SingleOperation() {
    }

    public SingleOperation(Long walletId, Long baseOperationId, Float amountOfMoney, Date date, Boolean completed, String category) {
        this.walletId = walletId;
        this.baseOperationId = baseOperationId;
        this.amountOfMoney = amountOfMoney;
        this.date = date;
        this.completed = completed;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public Long getBaseOperationId() {
        return baseOperationId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public Float getAmountOfMoney() {
        return amountOfMoney;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getCategory() {
        return category;
    }
}
