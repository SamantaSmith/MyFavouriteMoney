package com.example.myfavouritemoney.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "single_operation")
public class SingleOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="base_operation_id")
    private UUID baseOperationId;
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

    public SingleOperation(Long walletId, UUID baseOperationId, Float amountOfMoney, Date date, Boolean completed, String category) {
        this.walletId = walletId;
        this.baseOperationId = baseOperationId;
        this.amountOfMoney = amountOfMoney;
        this.date = date;
        this.completed = completed;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBaseOperationId() {
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
