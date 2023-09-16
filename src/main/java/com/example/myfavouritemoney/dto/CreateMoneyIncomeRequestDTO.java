package com.example.myfavouritemoney.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

@Schema
public record CreateMoneyIncomeRequestDTO (
        @SchemaProperty(name = "walletId") Long walletId,
        @SchemaProperty(name = "monthRegularity") Integer monthRegularity,
        @SchemaProperty(name = "income") Float income,
        @SchemaProperty(name = "standardDayOfIncomeInMonth") Integer standardDayOfIncomeInMonth
) {
    @JsonCreator
    public CreateMoneyIncomeRequestDTO(@JsonProperty(value = "walletId", required = true) Long walletId,
                                       @JsonProperty(value = "monthRegularity", required = true) Integer monthRegularity,
                                       @JsonProperty(value = "income", required = true) Float income,
                                       @JsonProperty(value = "standardDayOfIncomeInMonth", required = true) Integer standardDayOfIncomeInMonth) {
        this.walletId = walletId;
        this.monthRegularity = monthRegularity;
        this.income = income;
        this.standardDayOfIncomeInMonth = standardDayOfIncomeInMonth;
    }

    @Override
    @JsonProperty(value = "walletId", required = true)
    public Long walletId() {
        return walletId;
    }

    @Override
    @JsonProperty(value = "monthRegularity", required = true)
    public Integer monthRegularity() {
        return monthRegularity;
    }

    @Override
    @JsonProperty(value = "income", required = true)
    public Float income() {
        return income;
    }

    @Override
    @JsonProperty(value = "standardDayOfIncomeInMonth", required = true)
    public Integer standardDayOfIncomeInMonth() {
        return standardDayOfIncomeInMonth;
    }
}
