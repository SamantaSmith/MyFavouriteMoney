package com.example.myfavouritemoney.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "regular_operation")
public class RegularOperation {
    @Id
    private UUID id;
    @Column(name="base_operation_id")
    private UUID baseOperationId;
    @Column(name="period_of_regularity")
    private String periodOfRegularity;
    @Column(name="dates")
    private int[] dates;
    @Column(name="amount_of_money")
    private Float amountOfMoney;
    @Column(name="name")
    private String name;
    public RegularOperation() {
    }

    public RegularOperation(UUID id, UUID baseOperationId, String periodOfRegularity, int[] dates, Float amountOfMoney, String name) {
        this.id = id;
        this.baseOperationId = baseOperationId;
        this.periodOfRegularity = periodOfRegularity;
        this.dates = dates;
        this.amountOfMoney = amountOfMoney;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBaseOperationId() {
        return baseOperationId;
    }

    public String getPeriodOfRegularity() {
        return periodOfRegularity;
    }

    public int[] getDates() {
        return dates;
    }

    public Float getAmountOfMoney() {
        return amountOfMoney;
    }

    public String getName() {
        return name;
    }
}
