package com.mercadolibre.socialmeli_g3.repository;

import com.mercadolibre.socialmeli_g3.entity.User;

public interface IUserRepository {

    User getFollowers(int userId);
    User findUserById(int userId);
    void unfollow(User user, User userToUnfollow);
    User follow(User user, User userToFollow);
}
