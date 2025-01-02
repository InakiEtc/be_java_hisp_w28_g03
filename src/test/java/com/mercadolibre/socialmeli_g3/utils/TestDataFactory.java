package com.mercadolibre.socialmeli_g3.utils;

import com.mercadolibre.socialmeli_g3.dto.PromoProductPostDTO;
import com.mercadolibre.socialmeli_g3.dto.response.FollowDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.dto.response.FollowersListDTO;
import com.mercadolibre.socialmeli_g3.dto.response.UserDTO;
import com.mercadolibre.socialmeli_g3.entity.User;

import java.util.ArrayList;
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

    private final static ProductPostDTO post200 = new ProductPostDTO(
            3, "29-04-2021",
            new ProductDTO(104, "Monitor Gamer", "Gamer", "AOC", "Black", "Curved"),
            100, 1500.50);

    private final static ProductPostDTO post400 = new ProductPostDTO(
            2, "29-04-2021",
            new ProductDTO(104, "Monitor Gamer", "Gamer", "AOC", "Black", "Curved"),
            100, 1500.50);

    private final static ProductPostDTO post404 = new ProductPostDTO(
            3, "29-04-2021",
            new ProductDTO(999,"Fake Product", "Fake Category", "Fake Brand", "Fake Color", "Fake Model"),
            100, 1500.50);

    private final static PromoProductPostDTO promoPost200 = new PromoProductPostDTO(
            3, "29-04-2021",
            new ProductDTO(101, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition"),
            100, 1500.50, true, 0.25);

    private final static PromoProductPostDTO promoPost400 = new PromoProductPostDTO(
            2, "29-04-2021",
            new ProductDTO(101, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition"),
            100, 1500.50, true, 33.33);

    private final static PromoProductPostDTO promoPost404 = new PromoProductPostDTO(
            3, "29-04-2021",
            new ProductDTO(999,"Fake Product", "Fake Category", "Fake Brand", "Fake Color", "Fake Model"),
            100, 1500.50, true, 33.33);


    public static ProductPostDTO getPost200() { return post200; }
    public static ProductPostDTO getPost400() { return post400; }
    public static ProductPostDTO getPost404() { return post404; }
    public static PromoProductPostDTO getPromoPost200() { return promoPost200; }
    public static PromoProductPostDTO getPromoPost400() { return promoPost400; }
    public static PromoProductPostDTO getPromoPost404() { return promoPost404; }

    public static List<User> getVendedor1FollowersAsc() {
        return new ArrayList<>(List.of(user6, user2, user3));
    }

    public static List<User> getVendedor1FollowersDesc() {
        return new ArrayList<>(List.of(user3, user2, user6));
    }

    public static User getVendedor1() {
        return user1;
    }

    public static FollowersListDTO getVendedor1FollowersDTOAsc() {
        FollowersListDTO user1Followers = new FollowersListDTO();
        List<UserDTO> users = new ArrayList<>(List.of(user6DTO, user2DTO, user3DTO));
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
