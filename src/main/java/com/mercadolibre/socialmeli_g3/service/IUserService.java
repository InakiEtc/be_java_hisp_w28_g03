package com.mercadolibre.socialmeli_g3.service;
import com.mercadolibre.socialmeli_g3.dto.FollowedListDTO;
import com.mercadolibre.socialmeli_g3.dto.FollowersListDTO;
import com.mercadolibre.socialmeli_g3.dto.response.FollowDTO;

import com.mercadolibre.socialmeli_g3.dto.FollowersCountDTO;
import com.mercadolibre.socialmeli_g3.dto.UserDTO;

import java.util.List;

public interface IUserService {

    FollowersListDTO getSellerFollowers(int userId);
    FollowedListDTO getFollowedByUserId(int id);
    void unfollow(int userId, int userIdToUnfollow);
    FollowDTO follow(int userId, int userIdToFollow);
    List<UserDTO> searchAllUser();
    FollowersCountDTO getNumberFollowers(int userId);

}
