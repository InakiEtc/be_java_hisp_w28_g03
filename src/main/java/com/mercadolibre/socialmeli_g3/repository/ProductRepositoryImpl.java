package com.mercadolibre.socialmeli_g3.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements IProductRepository{

    @Autowired
    private IProductRepository productRepository;

    public ProductRepositoryImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
