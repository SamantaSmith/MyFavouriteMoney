package com.example.myfavouritemoney.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name= "regular_operation_unit")
public class RegularOperationUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="base_regular_operation_id")
    private UUID baseRegularOperationId;
    @Column(name="real_amount_of_money")
    private Float realAmountOfMoney;
    @Column(name="unit_operation_date")
    private LocalDate unitOperationDate;
    @Column(name="completed")
    private Boolean completed;
    @Column(name="category")
    private String category;
    public RegularOperationUnit() {
    }

    public RegularOperationUnit(UUID id, UUID baseRegularOperationId, Float realAmountOfMoney, LocalDate unitOperationDate, Boolean completed, String category) {
        this.id = id;
        this.baseRegularOperationId = baseRegularOperationId;
        this.realAmountOfMoney = realAmountOfMoney;
        this.unitOperationDate = unitOperationDate;
        this.completed = completed;
        this.category=category;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBaseRegularOperationId() {
        return baseRegularOperationId;
    }

    public Float getRealAmountOfMoney() {
        return realAmountOfMoney;
    }

    public LocalDate getUnitOperationDate() {
        return unitOperationDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getCategory() {
        return category;
    }
}
