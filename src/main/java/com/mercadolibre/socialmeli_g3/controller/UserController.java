package com.mercadolibre.socialmeli_g3.controller;

import com.mercadolibre.socialmeli_g3.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final IUserService postService;

    public UserController(IUserService postService) {
        this.postService = postService;
    }

    @GetMapping("/users/{userId}/followers/list")
    public ResponseEntity<?> getPosts(@PathVariable int userId){
        return new ResponseEntity<>(postService.getSellerFollowers(userId), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollow(@PathVariable int userId, @PathVariable int userIdToUnfollow){
        postService.unfollow(userId, userIdToUnfollow);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
