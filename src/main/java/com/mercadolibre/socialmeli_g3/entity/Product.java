package com.mercadolibre.socialmeli_g3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int productId;
    private String productName;
    private String type;
    private String brand;
    private String color;
    private String notes;

}