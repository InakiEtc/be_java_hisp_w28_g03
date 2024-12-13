package com.mercadolibre.socialmeli_g3.service;


import com.mercadolibre.socialmeli_g3.dto.PromoProductsCountDTO;
import com.mercadolibre.socialmeli_g3.dto.response.ProductoByIdUserResponseDTO;
import com.mercadolibre.socialmeli_g3.entity.Post;

import java.util.List;

public interface IPostService {

    List<Post> getPosts();
    ProductoByIdUserResponseDTO findProductByIdUser(int userId);
    PromoProductsCountDTO findProductsPromoCount(int userId);
}
