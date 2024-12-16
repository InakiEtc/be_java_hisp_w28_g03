package com.mercadolibre.socialmeli_g3.controller;

import com.mercadolibre.socialmeli_g3.dto.PromoProductPostDTO;
import com.mercadolibre.socialmeli_g3.dto.response.ProductByIdUserResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.response.findProductsPromoResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.service.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<ProductByIdUserResponseDTO> findProductByIdUser(@PathVariable int userId, @RequestParam(required = false) String order){
        return new ResponseEntity<ProductByIdUserResponseDTO>(postService.findProductByIdUser(userId, order), HttpStatus.OK);
    }

    @GetMapping("/products/promo-post/count")
    public ResponseEntity<findProductsPromoResponseDTO> findProductsPromoCount(@RequestParam int user_id){
        return new ResponseEntity<findProductsPromoResponseDTO>(postService.findProductsPromoCount(user_id), HttpStatus.OK);
    }

    @PostMapping("/products/post")
    public ResponseEntity<?> createPost(@RequestBody ProductPostDTO productPostDTO){
        return new ResponseEntity<>(postService.createPost(productPostDTO),HttpStatus.OK);
    }

    @GetMapping("/products/promo-post/list")
    public ResponseEntity<PromoProductPostDTO> findProdutsOnPromoByUser(@RequestParam int user_id){
        return new ResponseEntity<>(postService.getProductsOnPromoByUser(user_id), HttpStatus.OK);
    }
}
