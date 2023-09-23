package com.example.myfavouritemoney.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="wallet_type")
@Entity
public class WalletType {

    @Column(name="type_int")
    @Id
    private Long typeInt;
    @Column(name="type_varchar")
    private String typeVarchar;

    public WalletType(Long typeInt, String typeVarchar) {
        this.typeInt = typeInt;
        this.typeVarchar = typeVarchar;
    }

    public WalletType() {

    }

    public Long getTypeInt() {
        return typeInt;
    }

    public String getTypeVarchar() {
        return typeVarchar;
    }
}
