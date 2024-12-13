package com.mercadolibre.socialmeli_g3.service;

import com.mercadolibre.socialmeli_g3.dto.FollowedListDTO;

public interface IUserService {


    FollowedListDTO getFollowedByUserId(int id);

}
