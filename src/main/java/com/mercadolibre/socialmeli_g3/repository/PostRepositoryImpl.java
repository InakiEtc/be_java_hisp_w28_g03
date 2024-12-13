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

@Repository
public class PostRepositoryImpl implements IPostRepository{
    private List<Post> postsList;

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


}
