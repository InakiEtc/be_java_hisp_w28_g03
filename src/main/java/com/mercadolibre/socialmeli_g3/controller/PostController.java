package com.mercadolibre.socialmeli_g3.controller;

import com.mercadolibre.socialmeli_g3.dto.PostDTO;
import com.mercadolibre.socialmeli_g3.dto.PromoProductPostDTO;
import com.mercadolibre.socialmeli_g3.dto.PromoProductPostListDTO;
import com.mercadolibre.socialmeli_g3.dto.*;
import com.mercadolibre.socialmeli_g3.dto.response.ProductByIdUserResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.response.findProductsPromoResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.entity.Post;
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
import java.util.Map;

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

    @PostMapping("/products/promo-post")
    public ResponseEntity<?> createPromoPost(@RequestBody PromoProductPostDTO promoProductPostDTO){
        return new ResponseEntity<>(postService.createPromoPost(promoProductPostDTO),HttpStatus.OK);
    }

    @GetMapping("/products/promo-post/list")
    public ResponseEntity<PromoProductPostListDTO> findProductsOnPromoByUser(@RequestParam String user_id){
        return new ResponseEntity<>(postService.getProductsOnPromoByUser(user_id), HttpStatus.OK);
    }

    // US017
    @GetMapping("/products/posts/by-product-attributes/")
    public ResponseEntity<List<PostDTO>> findProductsByProductAttributes(@RequestParam Map<String, String> filterParams) {
        return new ResponseEntity<>(postService.getPostsByProductAttributes(filterParams), HttpStatus.OK);
    }
    //CU 016
    @GetMapping ("/products/post/category/{category}")
    public ResponseEntity<?> findProductByCategory(@PathVariable  int category){
        return new ResponseEntity<>(postService.findProductsByCategory(category), HttpStatus.OK);
    }





}
