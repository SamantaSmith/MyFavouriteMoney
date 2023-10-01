package com.example.myfavouritemoney.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

@Schema
public record CreateMoneyOperationRequestDTO(
        @SchemaProperty(name = "operationType") String operationType,
        @SchemaProperty(name = "regularityType") String regularityType

) {


}
