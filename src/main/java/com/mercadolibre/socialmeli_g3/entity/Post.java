package com.mercadolibre.socialmeli_g3.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private int postId;
    @JsonProperty("user_id")
    private int userId;
    private String date;
    private Product product;
    private int category;
    private double price;
}