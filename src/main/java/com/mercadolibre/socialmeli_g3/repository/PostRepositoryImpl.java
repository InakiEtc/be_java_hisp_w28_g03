package com.mercadolibre.socialmeli_g3.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.entity.Post;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements IPostRepository{
    private List<Post> postsList;
    private static Integer CONTADOR_POSTS;

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
        CONTADOR_POSTS = postsList.size() + 200;
    }

    @Override
    public List<Post> findAllPosts() {
        return postsList;
    }

    @Override
    public List<Post> findAllPostByUser(int userId) {

//        List<Post> postsList.stream().filter(p->p.getUserId()==userId).toList();
        return null;
    }

    @Override
    public Optional<Post> findPostById(Integer postId) {
        return postsList.stream().filter(p -> p.getPostId() == postId).findFirst();
    }

    @Override
    public void createPost(Post post) {
        CONTADOR_POSTS++;
        post.setPostId(CONTADOR_POSTS);
        postsList.add(post);
    }


}
