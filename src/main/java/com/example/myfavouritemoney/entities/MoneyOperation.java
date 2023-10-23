package com.example.myfavouritemoney.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "money_operation")
public class MoneyOperation {

    @Id
    private UUID id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="operation_type")
    private String operationType;
    @Column(name="regularity_type")
    private String regularityType;
    @Column(name="single_operation_id")
    private UUID singleOperationId;
    @Column(name="regular_operation_id")
    private UUID regularOperationId;
    @Column(name="actual")
    private Boolean actual;

    public MoneyOperation(UUID id, Long userId, String operationType, String regularityType, UUID singleOperationId, UUID regularOperationId, Boolean actual) {
        this.id = id;
        this.userId = userId;
        this.operationType = operationType;
        this.regularityType = regularityType;
        this.singleOperationId = singleOperationId;
        this.regularOperationId = regularOperationId;
        this.actual = actual;
    }

    public UUID getId() {
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

    public UUID getSingleOperationId() {
        return singleOperationId;
    }

    public UUID getRegularOperationId() {
        return regularOperationId;
    }

    public Boolean getActual() {
        return actual;
    }

    public MoneyOperation() {

    }
}
