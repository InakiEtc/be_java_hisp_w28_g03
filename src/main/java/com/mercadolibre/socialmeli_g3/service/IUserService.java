package com.mercadolibre.socialmeli_g3.service;
import com.mercadolibre.socialmeli_g3.dto.FollowedListDTO;
import com.mercadolibre.socialmeli_g3.dto.FollowersListDTO;
import com.mercadolibre.socialmeli_g3.dto.response.FollowDTO;

public interface IUserService {

    FollowersListDTO getSellerFollowers(int userId);
    FollowedListDTO getFollowedByUserId(int id);
    void unfollow(int userId, int userIdToUnfollow);
    FollowDTO follow(int userId, int userIdToFollow);
}
