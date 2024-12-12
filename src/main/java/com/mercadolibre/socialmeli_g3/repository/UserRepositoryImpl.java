package com.mercadolibre.socialmeli_g3.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements IUserRepository{

    @Autowired
    private IUserRepository userRepository;

    public UserRepositoryImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
