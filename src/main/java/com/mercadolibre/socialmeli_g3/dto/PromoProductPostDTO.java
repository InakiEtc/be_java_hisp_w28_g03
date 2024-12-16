package com.mercadolibre.socialmeli_g3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoProductPostDTO {
    private int userId;
    private String username;
    private List<PostDTO> posts;
}