package com.mercadolibre.socialmeli_g3.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.exception.InvalidOperationException;
import com.mercadolibre.socialmeli_g3.exception.NotFoundException;
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

        User user = usersList
                .stream()
                .filter(userData -> userData.getUserId() == userId)
                .findFirst()
                .orElse(null);

        if (user == null) throw new NotFoundException("No se encontró el vendedor");

        return user;
    }

    @Override
    public void unfollow(int userId, int userIdToUnfollow) {

        if (userId == userIdToUnfollow) throw new InvalidOperationException("No puedes dejar de seguirte a ti mismo");

        User user = usersList
                .stream()
                .filter(userData -> userData.getUserId() == userId)
                .findFirst()
                .orElse(null);

        if (user == null) throw new NotFoundException("No se encontró el vendedor");

        User userToUnfollow = usersList
                .stream()
                .filter(userData -> userData.getUserId() == userIdToUnfollow)
                .findFirst()
                .orElse(null);

        if (userToUnfollow == null) throw new NotFoundException("No se encontró el vendedor a dejar de seguir");

        if (user.getFollowed().contains(userToUnfollow)) {
            user.getFollowed().remove(userToUnfollow);
        } else {
            throw new NotFoundException("El usuario no esta en tu lista de seguidos");
        }

        if (userToUnfollow.getFollowers().contains(user)) {
            userToUnfollow.getFollowers().remove(user);
        } else {
            throw new NotFoundException("El usuario no esta en la lista de seguidores");
        }
    }
}
