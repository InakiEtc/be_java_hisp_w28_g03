package com.mercadolibre.socialmeli_g3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private IPostService postService;

    public PostServiceImpl(IPostService postService) {
        this.postService = postService;
    }
}
