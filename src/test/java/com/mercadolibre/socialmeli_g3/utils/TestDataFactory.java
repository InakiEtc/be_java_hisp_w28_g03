package com.mercadolibre.socialmeli_g3.utils;

import com.mercadolibre.socialmeli_g3.dto.response.*;
import com.mercadolibre.socialmeli_g3.entity.Post;
import com.mercadolibre.socialmeli_g3.entity.Product;
import com.mercadolibre.socialmeli_g3.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDataFactory {
    private final static User user2 = new User(2, "usuario1", null, null, null);
    private final static User user3 = new User(3, "usuario2", null, null, null);
    private final static User user6 = new User(6, "usuario 6", null, null, null);
    private final static User user4 = new User(4, "vendedor2", null, null,null);
    private final static User user5 = new User(5, "vendedor3", null, null,null);
    private final static User user1 = new User(1, "vendedor1", new ArrayList<>(List.of(user6, user2, user3)), new ArrayList<>(List.of(user2, user4, user5)), null);
    private final static UserDTO user2DTO = new UserDTO(2, "usuario1");
    private final static UserDTO user3DTO = new UserDTO(3, "usuario2");
    private final static  UserDTO user6DTO = new UserDTO(6, "usuario 6");
    private final static FollowDTO followDTO = new FollowDTO(1, 6);
    private final static FindProductsPromoResponseDTO promoPost = new FindProductsPromoResponseDTO(1, "vendedor1", 3);
    private final static  Post post = new Post (
            1001,
            1000,
            null,
            new Product(
                    1002,
                    "Silla Gamer",
                    "Gamer",
                    "Racer",
                    "Red & Black",
                    "Special Edition"
            ),
            100,
            1500.50,
            true,
            0.40
    );
    private final static Post post2 = new Post (
            1002,
            1000,
            null,
            new Product(
                    1003,
                    "Silla Gamer",
                    "Gamer",
                    "Racer",
                    "Red & Black",
                    "Special Edition"
            ),
            100,
            1500.50,
            true,
            0.40
    );
    private final static Post post3 = new Post (
            1003,
            1000,
            null,
            new Product(
                    1004,
                    "Silla Gamer",
                    "Gamer",
                    "Racer",
                    "Red & Black",
                    "Special Edition"
            ),
            100,
            1500.50,
            true,
            0.40
    );
    private final static ProductResponseDTO productResponseDTO = new ProductResponseDTO(
            101,
            "Silla Gamer",
            "Gamer",
            "Racer",
            "Red & Black",
            "Special Edition"
    );
    private final static ProductResponseDTO product2ResponseDTO = new ProductResponseDTO(
            102,
            "Teclado Mecánico",
            "Teclado",
            "Logitech",
            "Black",
            "RGB Backlit"
    );
    private final static ProductResponseDTO product3ResponseDTO = new ProductResponseDTO(
            103,
            "Mouse Gamer",
            "Gamer",
            "Razer",
            "Green",
            "Wireless"
    );

    private final static PostResponseDto postResponseDTO = new PostResponseDto(
            201,
            1,
            "20-12-2024",
            productResponseDTO,
            100,
            1500.5

    );
    private final static PostResponseDto post2ResponseDTO = new PostResponseDto(
            202,
            1,
            "21-11-2024",
            product2ResponseDTO,
            58,
            250.0

    );
    private final static PostResponseDto post3ResponseDTO = new PostResponseDto(
            203,
            1,
            "03-08-2023",
            product3ResponseDTO,
            60,
            120.0

    );

    private final static ProductByIdUserResponseDTO productByIdUserResponseDTO =  new ProductByIdUserResponseDTO( 1,new ArrayList<>(List.of(postResponseDTO)));
    private final static ProductByIdUserResponseDTO productByIdUserResponseDTOByOrderDesc =  new ProductByIdUserResponseDTO( 1,new ArrayList<>(List.of(post3ResponseDTO,postResponseDTO,post2ResponseDTO)));

    public static ProductByIdUserResponseDTO getProductByIdUserResponseDTO(){return productByIdUserResponseDTO;}
    public static ProductByIdUserResponseDTO getproductByIdUserResponseDTOByOrderDesc(){return productByIdUserResponseDTOByOrderDesc;}

    public static Post getPost() {return post;}

    public static Post getPost2() {return post2;}

    public static Post getPost3() {return post3;}

    public static List<Post> getListPost() {return new ArrayList<>(List.of(post,post2,post3));}

    public static List<User> getVendedor1FollowersAsc() {
        return new ArrayList<>(List.of(user6, user2, user3));
    }

    public static List<User> getVendedor1FollowersDesc() {
        return new ArrayList<>(List.of(user3, user2, user6));
    }

    public static User getVendedor1() {
        return user1;
    }

    public static FindProductsPromoResponseDTO getProductsPromo() {
        return promoPost;
    }

    public static FollowersListDTO getVendedor1FollowersDTOAsc() {
        FollowersListDTO user1Followers = new FollowersListDTO();
        List<UserDTO> users = new ArrayList<>(List.of(user6DTO, user2DTO, user3DTO));
        user1Followers.setUserId(1);
        user1Followers.setUserName("vendedor1");
        user1Followers.setFollowers(users);

        return user1Followers;
    }

    public static FollowersListDTO getVendedor1FollowersListDTO() {
        FollowersListDTO user1Followers = new FollowersListDTO();
        List<UserDTO> users = new ArrayList<>(List.of(user2DTO, user3DTO, user6DTO));
        user1Followers.setUserId(1);
        user1Followers.setUserName("vendedor1");
        user1Followers.setFollowers(users);

        return user1Followers;
    }

    public static FollowersListDTO getVendedor1FollowersDTODesc() {
        FollowersListDTO user1Followers = new FollowersListDTO();
        List<UserDTO> users = new ArrayList<>(List.of(user3DTO, user2DTO, user6DTO));
        user1Followers.setUserId(1);
        user1Followers.setUserName("vendedor1");
        user1Followers.setFollowers(users);

        return user1Followers;
    }

    public static FollowDTO getVendedor1FollowDTOUser6() {
        return followDTO;
    }
}
