package com.mercadolibre.socialmeli_g3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.PostDTO;
import com.mercadolibre.socialmeli_g3.dto.PromoProductsCountDTO;
import com.mercadolibre.socialmeli_g3.dto.response.ProductoByIdUserResponseDTO;
import com.mercadolibre.socialmeli_g3.entity.Post;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.exception.NotFoundException;
import com.mercadolibre.socialmeli_g3.repository.IPostPromoRepository;
import com.mercadolibre.socialmeli_g3.repository.IPostRepository;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final IPostPromoRepository promoPostRepository;

    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository, IPostPromoRepository promoPostRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.promoPostRepository = promoPostRepository;
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
        response.setPosts(postRepository.findProductByIdUser(userId).stream().map( post -> obj.convertValue(post, PostDTO.class)).toList());
        if(response.getPosts().isEmpty()){
            throw new NotFoundException("Post no encontrados");
        }
        return response;
    }

    @Override
    public PromoProductsCountDTO findProductsPromoCount(int userId) {
        PromoProductsCountDTO response = new PromoProductsCountDTO();
        User usuario = userRepository.findUserById(userId);
        if(usuario == null){
            throw new NotFoundException("El usuario no existe");
        }
        response.setUserId(userId);
        response.setUserName(usuario.getUserName());
        response.setPromoProductsCount(promoPostRepository.findProductsPromoCount(userId));
        return response;
    }
}
