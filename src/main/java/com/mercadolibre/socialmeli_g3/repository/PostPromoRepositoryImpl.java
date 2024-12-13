package com.mercadolibre.socialmeli_g3.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.entity.Post;
import com.mercadolibre.socialmeli_g3.entity.PromoPost;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostPromoRepositoryImpl implements IPostPromoRepository{
    private List<PromoPost> postsPromoList;

    public PostPromoRepositoryImpl() throws IOException {
        postsPromoList=new ArrayList<>();
        loadDataBase();
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<PromoPost> posts ;

        file= ResourceUtils.getFile("classpath:postPromoDB");
        posts= objectMapper.readValue(file,new TypeReference<List<PromoPost>>(){});

        postsPromoList = posts;
    }

    @Override
    public long findProductsPromoCount(int userId) {
        return (int) postsPromoList.stream().filter(x -> x.getUserId() == userId).count();
    }
}
