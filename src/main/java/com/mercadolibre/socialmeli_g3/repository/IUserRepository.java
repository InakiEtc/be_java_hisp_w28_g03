package com.mercadolibre.socialmeli_g3.repository;

import com.mercadolibre.socialmeli_g3.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    Optional<User> findUserById(int id);

}
