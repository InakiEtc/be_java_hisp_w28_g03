package com.mercadolibre.socialmeli_g3.unit.service;

import com.mercadolibre.socialmeli_g3.dto.response.FollowersListDTO;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import com.mercadolibre.socialmeli_g3.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("T-0004 - Method followersOrderBy should perform an ascending alphabetical sorting when 'name_asc' request param is provided")
    void test_followersOrderBy_should_perform_ascending_alphabetical_sorting() {
        String sortingParam = "name_asc";
        int userId = 1;
        User user = getVendedor1();
        List<User> expectedFollowersList = getVendedor1FollowersAsc();
        FollowersListDTO expectedVendedor1Followers = getVendedor1FollowersDTOAsc();


        when(userRepository.findUserById(userId)).thenReturn(user);
        when(userRepository.findFollowersOrderedByName(userId, sortingParam)).thenReturn(expectedFollowersList);

        FollowersListDTO obtainedVendedor1Followers = userService.followersOrderBy(userId, sortingParam);

        assertEquals(expectedVendedor1Followers, obtainedVendedor1Followers);
    }

    @Test
    @DisplayName("T-0004 - Method followersOrderBy should perform an descending alphabetical sorting when 'name_desc' request param is provided")
    void test_followersOrderBy_should_perform_descending_alphabetical_sorting() {
        String sortingParam = "name_desc";
        int userId = 1;
        User user = getVendedor1();
        List<User> expectedFollowersList = getVendedor1FollowersDesc();
        FollowersListDTO expectedVendedor1Followers = getVendedor1FollowersDTODesc();


        when(userRepository.findUserById(userId)).thenReturn(user);
        when(userRepository.findFollowersOrderedByName(userId, sortingParam)).thenReturn(expectedFollowersList);

        FollowersListDTO obtainedVendedor1Followers = userService.followersOrderBy(userId, sortingParam);

        assertEquals(expectedVendedor1Followers, obtainedVendedor1Followers);
    }
}