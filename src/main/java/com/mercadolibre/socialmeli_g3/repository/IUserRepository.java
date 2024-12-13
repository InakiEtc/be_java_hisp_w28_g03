package com.mercadolibre.socialmeli_g3.repository;

import com.mercadolibre.socialmeli_g3.entity.User;

import java.util.List;

public interface IUserRepository {
    List<User> findAllUsers();
    List<User> getFollowers();
    User findUserById(int userId);
}
