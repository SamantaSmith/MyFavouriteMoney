package com.example.myfavouritemoney.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="actualize_data")
public class ActualizeData {

    @Id
    private UUID id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="wallet_actualize_date")
    private LocalDate walletActualizeDate;

    public ActualizeData() {
    }

    public ActualizeData(UUID id, Long userId, LocalDate walletActualizeDate) {
        this.id = id;
        this.userId = userId;
        this.walletActualizeDate = walletActualizeDate;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDate getWalletActualizeDate() {
        return walletActualizeDate;
    }
}
