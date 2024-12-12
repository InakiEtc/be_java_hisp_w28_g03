package com.mercadolibre.socialmeli_g3.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements IPostRepository{

    @Autowired
    private IPostRepository postRepository;

    public PostRepositoryImpl(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
