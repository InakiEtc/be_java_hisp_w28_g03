package com.mercadolibre.socialmeli_g3.service;

import com.mercadolibre.socialmeli_g3.dto.FollowersCountDTO;
import com.mercadolibre.socialmeli_g3.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> searchAllUser();
    FollowersCountDTO getNumberFollowers(int userId);

}
