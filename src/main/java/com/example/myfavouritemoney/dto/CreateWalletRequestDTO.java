package com.example.myfavouritemoney.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

@Schema
public record CreateWalletRequestDTO(@SchemaProperty (name = "name") String name,
                                     @SchemaProperty (name = "type") Integer type,
                                     @SchemaProperty (name = "initialMoney") Float initialMoney) {

    @JsonCreator
    public CreateWalletRequestDTO(
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "type", required = true) Integer type,
            @JsonProperty(value = "initialMoney", required = true) Float initialMoney) {
        this.name = name;
        this.type = type;
        this.initialMoney = initialMoney;
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
    @JsonProperty(value = "initialMoney", required = true)
    public Float initialMoney() {
        return initialMoney;
    }
}
