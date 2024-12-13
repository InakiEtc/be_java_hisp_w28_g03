package com.mercadolibre.socialmeli_g3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.socialmeli_g3.dto.MessageDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.entity.Post;
import com.mercadolibre.socialmeli_g3.exception.BadRequestException;
import com.mercadolibre.socialmeli_g3.repository.IPostRepository;
import com.mercadolibre.socialmeli_g3.repository.IProductRepository;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final IProductRepository productRepository;
    private final IUserRepository userRepository;
    private ObjectMapper objectMapper;

    public PostServiceImpl(IPostRepository postRepository, IProductRepository productRepository, IUserRepository userRepository) {
        this.postRepository = postRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAllPosts();
   }

    @Override
    public MessageDTO createPost(ProductPostDTO productPostDTO) {
        if (userRepository.findUserById(productPostDTO.getUserId()).isEmpty()) {
            throw new BadRequestException("User not found");
        }

        if (postRepository.findAllPosts().stream().anyMatch(p -> p.getUserId() == productPostDTO.getUserId() &&
                p.getProduct().getProductId() == productPostDTO.getProduct().getProductId())) {
            throw new BadRequestException("Post already exists for this user and product");
        }

        if (productPostDTO.getProduct() == null || productRepository.findProductById(productPostDTO.getProduct().getProductId()).isEmpty()) {
            throw new BadRequestException("Bad request in Product");
        }

        try {
            int category = Integer.parseInt(String.valueOf(productPostDTO.getCategory()));
            if (category < 0) {
                throw new BadRequestException("Category must be positive");
            }
        } catch (NumberFormatException e) {
            throw new BadRequestException("Category must be an integer");
        }

        try {
            double price = Double.parseDouble(String.valueOf(productPostDTO.getPrice()));
            if (price < 0) {
                throw new BadRequestException("Price must be positive");
            }
        } catch (NumberFormatException e) {
            throw new BadRequestException("Price must be a double");
        }

        postRepository.createPost(objectMapper.convertValue(productPostDTO, Post.class));
        return new MessageDTO("Post created successfully");
    }
}
