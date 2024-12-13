package com.mercadolibre.socialmeli_g3.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository{


    private List<User> usersList;

    public UserRepositoryImpl() throws IOException {
        usersList=new ArrayList<>();
        loadDataBase();
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users ;

        file= ResourceUtils.getFile("classpath:usersDb.json");
        users= objectMapper.readValue(file,new TypeReference<List<User>>(){});

        usersList = users;
    }

    @Override
    public User getFollowers(int userId) {

        User usuario = usersList
                .stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .orElse(null);

        return usuario;
    }
}
