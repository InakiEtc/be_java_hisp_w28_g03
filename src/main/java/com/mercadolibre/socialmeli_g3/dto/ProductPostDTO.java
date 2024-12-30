package com.mercadolibre.socialmeli_g3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPostDTO {

    @JsonProperty(value = "user_id")
    @NotNull(message = "The user id must be provided")
    @Positive(message = "The user id must be a positive number")
    private Integer userId;

    @NotNull(message = "The date must be provided")
    private String date;

    @Valid
    private ProductDTO product;

    @NotNull(message = "The category must be provided")
    private Integer category;

    @NotNull(message = "The price must be provided")
    @Positive(message = "The price must be a positive number")
    @Max(value = 10000000, message = "The price must be less than 10000000")
    private Double price;
}
