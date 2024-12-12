package com.mercadolibre.socialmeli_g3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private int postId;
    private int userId;
    private String date;
    private Product product;
    private int category;
    private double price;

}