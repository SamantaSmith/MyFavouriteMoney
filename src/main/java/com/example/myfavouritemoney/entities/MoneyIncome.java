package com.example.myfavouritemoney.entities;

import jakarta.persistence.*;

@Entity
@Table(name="money_income")
public class MoneyIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="wallet_id")
    private Long walletId;
    @Column(name="month_regularity")
    private Integer monthRegularity;
    @Column(name="income")
    private Float income;
    @Column(name = "standard_day_of_income")
    private Integer standardDayOfIncomeInMonth;

    public MoneyIncome(Long userId, Long walletId, Integer monthRegularity, Float income, Integer standardDayOfIncomeInMonth) {
        this.userId = userId;
        this.walletId = walletId;
        this.monthRegularity = monthRegularity;
        this.income = income;
        this.standardDayOfIncomeInMonth = standardDayOfIncomeInMonth;
    }

    public MoneyIncome() {

    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public Integer getMonthRegularity() {
        return monthRegularity;
    }

    public Float getIncome() {
        return income;
    }

    public Integer getStandardDayOfIncomeInMonth() {
        return standardDayOfIncomeInMonth;
    }
}
