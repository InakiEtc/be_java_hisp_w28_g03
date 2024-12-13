package com.mercadolibre.socialmeli_g3.service;


import com.mercadolibre.socialmeli_g3.dto.response.ProductByIdUserResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.response.findProductsPromoResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.MessageDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.entity.Post;

import java.util.List;

public interface IPostService {

    List<Post> getPosts();
    ProductByIdUserResponseDTO findProductByIdUser(int userId);
    findProductsPromoResponseDTO findProductsPromoCount(int userId);
    MessageDTO createPost(ProductPostDTO productPostDTO);
}
