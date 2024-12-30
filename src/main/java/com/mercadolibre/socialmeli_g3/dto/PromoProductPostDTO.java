package com.mercadolibre.socialmeli_g3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PromoProductPostDTO {

    @NotNull(message = "The user id must be provided")
    @Positive(message = "The user id must be a positive number")
    @JsonProperty("user_id")
    private Integer userId;

    @NotNull(message = "The date must be provided")
    private String date;

    @Valid
    private ProductDTO product;

    @NotBlank(message = "The category must be provided")
    private Integer category;

    @NotNull(message = "The price must be provided")
    @Positive(message = "The price must be a positive number")
    @Max(value = 10000000, message = "The price must be less than 10000000")
    private Double price;

    @JsonProperty("has_promo")
    private Boolean hasPromo;

    private Double discount;
}