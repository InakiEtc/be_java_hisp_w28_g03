package com.mercadolibre.socialmeli_g3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.PostDTO;
import com.mercadolibre.socialmeli_g3.dto.PromoProductsCountDTO;
import com.mercadolibre.socialmeli_g3.dto.response.PostResponseDto;
import com.mercadolibre.socialmeli_g3.dto.response.ProductResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.response.ProductoByIdUserResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.response.findProductsPromoResponseDTO;
import com.mercadolibre.socialmeli_g3.entity.Post;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.exception.NotFoundException;
import com.mercadolibre.socialmeli_g3.repository.IPostRepository;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAllPosts();
   }

    @Override
    public ProductoByIdUserResponseDTO findProductByIdUser(int userId) {
        ProductoByIdUserResponseDTO response = new ProductoByIdUserResponseDTO();
        ObjectMapper obj = new ObjectMapper();
        response.setUser_id(userId);
        response.setPosts(postRepository.findProductByIdUser(userId).stream().map( post -> {
        PostResponseDto res = new PostResponseDto();
            ProductResponseDTO prodResponse = new ProductResponseDTO();
            prodResponse.setProduct_id(post.getProduct().getProductId());
            prodResponse.setType(post.getProduct().getType());
            prodResponse.setBrand(post.getProduct().getBrand());
            prodResponse.setColor(post.getProduct().getColor());
            prodResponse.setNotes(post.getProduct().getNotes());
            prodResponse.setProduct_name(post.getProduct().getProductName());

            res.setPost_id(post.getPostId());
            res.setUser_id(post.getUserId());
            res.setProduct(prodResponse);
            res.setCategory(post.getCategory());
            res.setPrice(post.getPrice());
            return res;
        }).toList());
        if(response.getPosts().isEmpty()){
            throw new NotFoundException("Post no encontrados");
        }
        return response;
    }

    @Override
    public findProductsPromoResponseDTO findProductsPromoCount(int userId) {
        findProductsPromoResponseDTO response = new findProductsPromoResponseDTO();
        User usuario = userRepository.findUserById(userId);
        if(usuario == null){
            throw new NotFoundException("El usuario no existe");
        }
        response.setUser_id(userId);
        response.setUser_name(usuario.getUserName());
        response.setPromos_products_count(postRepository.findProductsPromoCount(userId));
        return response;
    }
}
