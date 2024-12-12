package com.mercadolibre.socialmeli_g3.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private int postId;
    private int userId;
    private String date;
    private ProductDTO product;
    private int category;
    private double price;

}