package com.example.myfavouritemoney.entities;

import jakarta.persistence.*;

@Entity
@Table(name= "money_operation")
public class MoneyOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="operation_type")
    private String operationType;
    @Column(name="regularity_type")
    private String regularityType;
    @Column(name="single_income_id")
    private Long singleIncomeId;
    @Column(name="regular_income_id")
    private Long regularIncomeId;
    @Column(name="actual")
    private Boolean actual;

    public MoneyOperation(Long userId, String operationType, String regularityType, Long singleIncomeId, Long regularIncomeId, Boolean actual) {
        this.userId = userId;
        this.operationType = operationType;
        this.regularityType = regularityType;
        this.singleIncomeId = singleIncomeId;
        this.regularIncomeId = regularIncomeId;
        this.actual = actual;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getRegularityType() {
        return regularityType;
    }

    public Long getSingleIncomeId() {
        return singleIncomeId;
    }

    public Long getRegularIncomeId() {
        return regularIncomeId;
    }

    public Boolean getActual() {
        return actual;
    }

    public MoneyOperation() {

    }
}
