package com.mercadolibre.socialmeli_g3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserService userService;

    public UserServiceImpl(IUserService userService) {
        this.userService = userService;
    }
}
