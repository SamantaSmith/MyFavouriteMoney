package com.example.myfavouritemoney.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

@Schema
public record UpdateWalletRequestDTO(
                                     @SchemaProperty (name = "walletId") Long walletId,
                                     @SchemaProperty (name = "name") String name,
                                     @SchemaProperty (name = "type") Integer type,
                                     @SchemaProperty (name = "money") Float money)
{

    @JsonCreator
    public UpdateWalletRequestDTO(
            @JsonProperty(value = "walletId", required = true) Long walletId,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "type", required = true) Integer type,
            @JsonProperty(value = "money", required = true) Float money

    ) {
        this.walletId = walletId;
        this.name = name;
        this.type = type;
        this.money = money;
    }

    @Override
    @JsonProperty(value = "name", required = true)
    public String name() {
        return name;
    }

    @Override
    @JsonProperty(value = "type", required = true)
    public Integer type() {
        return type;
    }

    @Override
    @JsonProperty(value = "walletId", required = true)
    public Long walletId() {
        return walletId;
    }

    @Override
    @JsonProperty(value = "money", required = true)
    public Float money() {
        return money;
    }
}
