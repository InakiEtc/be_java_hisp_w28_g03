package com.mercadolibre.socialmeli_g3.repository;

import com.mercadolibre.socialmeli_g3.entity.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {

   List<Post> findAllPosts();
   Optional<Post> findPostById(Integer postId);
   void createPost(Post post);
   List<Post> findAllPostByUser(int userId);
}
