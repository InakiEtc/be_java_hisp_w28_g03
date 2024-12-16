package com.mercadolibre.socialmeli_g3.service;


import com.mercadolibre.socialmeli_g3.dto.PromoProductPostDTO;
import com.mercadolibre.socialmeli_g3.dto.PromoProductPostListDTO;
import com.mercadolibre.socialmeli_g3.dto.response.ProductByIdUserResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.response.findProductsPromoResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.MessageDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.entity.Post;

import java.util.List;

public interface IPostService {

    List<Post> getPosts();
    ProductByIdUserResponseDTO findProductByIdUser(int userId, String order);
    findProductsPromoResponseDTO findProductsPromoCount(int userId);
    MessageDTO createPost(ProductPostDTO productPostDTO);
    MessageDTO createPromoPost(PromoProductPostDTO promoProductPostDTO);
    PromoProductPostListDTO getProductsOnPromoByUser(String userId);
}
