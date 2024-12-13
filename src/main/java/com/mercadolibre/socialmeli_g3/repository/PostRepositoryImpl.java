package com.mercadolibre.socialmeli_g3.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.PromoProductsCountDTO;
import com.mercadolibre.socialmeli_g3.entity.Post;
import com.mercadolibre.socialmeli_g3.entity.PromoPost;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Post> findProductByIdUser(int userId) {
        return postsList.stream().filter( post ->  post.getUserId() == userId).sorted((post1, post2) -> post2.getDate().compareTo(post1.getDate())).collect(Collectors.toList());
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

}
