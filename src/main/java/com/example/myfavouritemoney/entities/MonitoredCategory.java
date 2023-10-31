package com.example.myfavouritemoney.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name= "monitored_category")
public class MonitoredCategory {

    @Id
    private UUID id;
    @Column(name="name")
    private String name;
    @Column(name="user_id")
    private Long userId;
    public MonitoredCategory() {
    }

    public MonitoredCategory(UUID id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getUserId() {
        return userId;
    }
}
