package com.mercadolibre.socialmeli_g3.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.entity.Post;

import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements IPostRepository{
    private List<Post> postsList;
    private static Integer POSTS_COUNTER;

    public PostRepositoryImpl() throws IOException {
        postsList=new ArrayList<>();
        loadDataBase();
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> posts ;

        file= ResourceUtils.getFile("classpath:postDB.json");
        posts= objectMapper.readValue(file,new TypeReference<List<Post>>(){});

        postsList = posts;
        POSTS_COUNTER = postsList.size() + 200;
    }

    @Override
    public List<Post> findAllPosts() {
        return postsList;
    }

    @Override
    public List<Post> findProductByIdUser(int userId) {
        return postsList.stream().filter( post ->  post.getUserId() == userId).sorted((post1, post2) -> post2.getDate().compareTo(post1.getDate())).collect(Collectors.toList());
    }

    @Override
    public List<Post> findProductByIdUserOrderedByDate(int userId, String order) {
        if(order.equalsIgnoreCase("date_asc")) {
            return findProductByIdUser(userId);
        }
        return postsList.stream().filter( post ->  post.getUserId() == userId).sorted(Comparator.comparing(Post::getDate)).toList();
    }

    @Override
    public int findProductsPromoCount(int userId) {
        List<Post> listProm = postsList.stream().filter(x -> x.getUserId() == userId).toList();
        int count = 0;
        for(Post prom: listProm){
            if(prom.isHasPromo()){
                count++;
            }
        }
        return count;
    }

    @Override
    public List<Post> findAllPostsOnPromoByUser(int userId) {
        return postsList.stream()
                .filter(p -> p.getUserId() == userId && p.isHasPromo())
                .toList();
    }

    @Override
    public Post findPostById(Integer postId) {
        return postsList.stream().filter(x -> x.getPostId() == postId).findFirst().orElse(null);
    }

    @Override
    public void createPost(Post post) {
        POSTS_COUNTER++;
        post.setPostId(POSTS_COUNTER);
        postsList.add(post);
    }
}
