package com.mercadolibre.socialmeli_g3.service;

import com.mercadolibre.socialmeli_g3.dto.FollowedListDTO;
import com.mercadolibre.socialmeli_g3.dto.FollowersListDTO;

public interface IUserService {

    FollowersListDTO getSellerFollowers(int userId);
}
