package com.mercadolibre.socialmeli_g3.repository;

import com.mercadolibre.socialmeli_g3.entity.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {

   List<Post> findAllPosts();
   public  List<Post> findProductByIdUser(int userId);
   List<Post> findProductByIdUserOrderedByDate(int userId, String order);
   public int findProductsPromoCount(int userId);
   Optional<Post> findPostById(Integer postId);
   void createPost(Post post);
   List<Post> findAllPostsOnPromoByUser(int userId);
}
