package com.mercadolibre.socialmeli_g3.controller;


import com.mercadolibre.socialmeli_g3.dto.response.ProductoByIdUserResponseDTO;
import com.mercadolibre.socialmeli_g3.entity.Post;
import com.mercadolibre.socialmeli_g3.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(){
        return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
    }

    @GetMapping("/products/followed/{userId}/list")
    public ResponseEntity<ProductoByIdUserResponseDTO> getPosts(@PathVariable int userId){
        return new ResponseEntity<ProductoByIdUserResponseDTO>(postService.findProductByIdUser(userId), HttpStatus.OK);
    }

}
