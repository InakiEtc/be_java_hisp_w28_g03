package com.mercadolibre.socialmeli_g3.dto.response;

import com.mercadolibre.socialmeli_g3.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private int post_id;
    private int user_id;
    private String date;
    private ProductResponseDTO product;
    private int category;
    private double price;
}
