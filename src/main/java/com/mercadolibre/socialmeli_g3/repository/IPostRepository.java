package com.mercadolibre.socialmeli_g3.repository;

import com.mercadolibre.socialmeli_g3.entity.Post;

import java.util.List;

public interface IPostRepository {

   List<Post> findAllPosts();

   List<Post> findAllPostByUser(int userId);
}
