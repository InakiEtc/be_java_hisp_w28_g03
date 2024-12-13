package com.mercadolibre.socialmeli_g3.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.entity.Post;
import com.mercadolibre.socialmeli_g3.entity.Product;
import com.mercadolibre.socialmeli_g3.entity.PromoPost;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
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

        file= ResourceUtils.getFile("classpath:postPromoDB.json");
        posts= objectMapper.readValue(file,new TypeReference<List<PromoPost>>(){});

        postsPromoList = posts;
    }

    @Override
    public int findProductsPromoCount(int userId) {
        List<PromoPost> listProm = postsPromoList.stream().filter(x -> x.getUserId() == userId).toList();
        int count = 0;
        for(PromoPost prom: listProm){
            if(prom.isHasPromo()){
                count++;
            }
        }
        return count;
    }
}
