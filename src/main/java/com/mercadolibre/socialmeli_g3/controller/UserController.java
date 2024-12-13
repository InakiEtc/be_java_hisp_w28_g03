package com.mercadolibre.socialmeli_g3.controller;

import com.mercadolibre.socialmeli_g3.dto.FollowedListDTO;
import com.mercadolibre.socialmeli_g3.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
