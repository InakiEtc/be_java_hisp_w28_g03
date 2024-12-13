package com.mercadolibre.socialmeli_g3.controller;

import com.mercadolibre.socialmeli_g3.service.IUserService;
import com.mercadolibre.socialmeli_g3.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    IUserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<?> getControllerFollowers(@PathVariable int userId){
        return new ResponseEntity<> (userService.getNumberFollowers(userId), HttpStatus.OK);
    }
}
