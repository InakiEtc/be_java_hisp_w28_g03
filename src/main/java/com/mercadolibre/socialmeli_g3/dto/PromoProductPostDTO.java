package com.mercadolibre.socialmeli_g3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoProductPostDTO {
    private int userId;
    private String date;
    private ProductDTO product;
    private int category;
    private double price;
    private boolean hasPromo;
    private double discount;
}