package com.mercadolibre.socialmeli_g3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.PromoProductPostDTO;
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
    public ProductByIdUserResponseDTO findProductByIdUser(int userId) {
        ProductByIdUserResponseDTO response = new ProductByIdUserResponseDTO();
        ObjectMapper obj = new ObjectMapper();
        response.setUser_id(userId);
        response.setPosts(postRepository.findProductByIdUser(userId).stream().map(post -> {
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
            res.setDate(
                    post.getDate()
            );
            return res;
        }).toList());
        if (response.getPosts().isEmpty()) {
            throw new NotFoundException("Post no encontrados");
        }
        return response;
    }

    @Override
    public findProductsPromoResponseDTO findProductsPromoCount(int userId) {
        findProductsPromoResponseDTO response = new findProductsPromoResponseDTO();
        User usuario = userRepository.findUserById(userId);
        if (usuario == null) {
            throw new NotFoundException("El usuario no existe");
        }
        response.setUser_id(userId);
        response.setUser_name(usuario.getUserName());
        response.setPromos_products_count(postRepository.findProductsPromoCount(userId));
        return response;
    }

    @Override
    public MessageDTO createPost(ProductPostDTO productPostDTO) {
        validateUser(productPostDTO.getUserId());
        validatePostExistence(productPostDTO.getUserId(), productPostDTO.getProduct().getProductId(), false);
        validateProduct(productPostDTO.getProduct().getProductId());
        validateCategory(productPostDTO.getCategory());
        validatePrice(productPostDTO.getPrice());

        postRepository.createPost(objectMapper.convertValue(productPostDTO, Post.class));
        return new MessageDTO("Post created successfully");
    }

    @Override
    public MessageDTO createPromoPost(PromoProductPostDTO promoProductPostDTO) {
        validateUser(promoProductPostDTO.getUserId());
        validatePostExistence(promoProductPostDTO.getUserId(), promoProductPostDTO.getProduct().getProductId(), true);
        validateProduct(promoProductPostDTO.getProduct().getProductId());
        validateCategory(promoProductPostDTO.getCategory());
        validatePrice(promoProductPostDTO.getPrice());
        validateDiscount(promoProductPostDTO.getDiscount());

        postRepository.createPost(objectMapper.convertValue(promoProductPostDTO, Post.class));
        return new MessageDTO("Post Promo created successfully");
    }

    // region Validations for Post methods
    private void validateUser(int userId) {
        if (userRepository.findUserById(userId) == null) {
            throw new BadRequestException("User not found");
        }
    }

    private void validatePostExistence(int userId, int productId, boolean isPromo) {
        if (postRepository.findAllPosts().stream().anyMatch(p -> p.getUserId() == userId &&
                p.getProduct().getProductId() == productId)) {
            throw new BadRequestException(isPromo ? "Post Promo already exists for this user and product" : "Post already exists for this user and product");
        }
    }

    private void validateProduct(int productId) {
        if (productRepository.findProductById(productId).isEmpty()) {
            throw new BadRequestException("Bad request in Product");
        }
    }

    private void validateCategory(int category) {
        if (category < 0) {
            throw new BadRequestException("Category must be positive");
        }
    }

    private void validatePrice(double price) {
        if (price < 0) {
            throw new BadRequestException("Price must be positive");
        }
    }

    private void validateDiscount(double discount) {
        if (discount < 0 || discount > 1) {
            throw new BadRequestException("Discount must be between 0 and 1");
        }
    }
    // endregion
}
