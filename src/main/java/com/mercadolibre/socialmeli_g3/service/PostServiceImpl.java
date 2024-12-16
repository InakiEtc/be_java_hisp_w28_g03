package com.mercadolibre.socialmeli_g3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.response.PostResponseDto;
import com.mercadolibre.socialmeli_g3.dto.response.ProductResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.response.ProductByIdUserResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.response.findProductsPromoResponseDTO;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.socialmeli_g3.dto.MessageDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.entity.Post;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.exception.NotFoundException;
import com.mercadolibre.socialmeli_g3.exception.BadRequestException;
import com.mercadolibre.socialmeli_g3.repository.IPostRepository;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import com.mercadolibre.socialmeli_g3.repository.IProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final IProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public PostServiceImpl(IPostRepository postRepository, IProductRepository productRepository, IUserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAllPosts();
   }

    @Override
    public ProductByIdUserResponseDTO findProductByIdUser(int userId, String order) {
        if(userId <= 0) {
            throw new BadRequestException("El user id proporcionado no es válido");
        }
        ProductByIdUserResponseDTO response = new ProductByIdUserResponseDTO();
        response.setUser_id(userId);
        List<Post> listOfPosts;
        if(order == null) {
            listOfPosts = postRepository.findProductByIdUser(userId);
        }
        else {
            validateOrder(order);
            listOfPosts = postRepository.findProductByIdUserOrderedByDate(userId, order);
        }
        List<PostResponseDto> posts = listOfPosts.stream().map( post -> {
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
            res.setDate(post.getDate());
            return res;
        }).toList();

        response.setPosts(posts);
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

    @Override
    public MessageDTO createPost(ProductPostDTO productPostDTO) {
        if (userRepository.findUserById(productPostDTO.getUserId()) == null) {
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

    private void validateOrder(String order) {
        if(!order.equalsIgnoreCase("date_asc") && !order.equalsIgnoreCase("date_desc")) {
            throw new BadRequestException("El orden provisto para ordenar por fecha no es válido");
        }
    }
}
