package com.mercadolibre.socialmeli_g3.repository;

import com.mercadolibre.socialmeli_g3.entity.Product;

import java.util.Optional;

public interface IProductRepository {
    Optional<Product> findProductById(int productId);
}
