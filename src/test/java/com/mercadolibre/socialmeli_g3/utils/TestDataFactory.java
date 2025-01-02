package com.mercadolibre.socialmeli_g3.utils;

import com.mercadolibre.socialmeli_g3.dto.response.FollowDTO;
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
