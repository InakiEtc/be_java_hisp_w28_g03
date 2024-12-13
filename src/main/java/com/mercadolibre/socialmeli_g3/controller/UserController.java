package com.mercadolibre.socialmeli_g3.controller;

import com.mercadolibre.socialmeli_g3.dto.FollowedListDTO;
import com.mercadolibre.socialmeli_g3.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<FollowedListDTO> getSellersFollowedByUser(@PathVariable int userId){
        return new ResponseEntity<>(iUserService.getFollowedByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<?> getPosts(@PathVariable int userId){
        return new ResponseEntity<>(iUserService.getSellerFollowers(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollow(@PathVariable int userId, @PathVariable int userIdToUnfollow){
        iUserService.unfollow(userId, userIdToUnfollow);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
