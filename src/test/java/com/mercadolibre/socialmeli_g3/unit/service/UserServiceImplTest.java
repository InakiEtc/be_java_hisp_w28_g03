package com.mercadolibre.socialmeli_g3.unit.service;

import com.mercadolibre.socialmeli_g3.dto.response.FollowersListDTO;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.exception.BadRequestException;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import com.mercadolibre.socialmeli_g3.exception.NotFoundException;
import com.mercadolibre.socialmeli_g3.repository.UserRepositoryImpl;
import com.mercadolibre.socialmeli_g3.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private static final User user =
            new User(1, "vendedor1",
                    List.of(new User(2, "usuario1", null, null,null),
                            new User(3, "usuario2", null, null,null),
                            new User(6, "usuario 6", null, null,null)),
                    List.of(new User(2, "usuario1", null, null,null),
                            new User(4, "vendedor2", null, null,null),
                            new User(5, "vendedor3", null, null,null)),
                    null);

    private static final User unfollowUser =
            new User(2, "usuario1",
                    List.of(new User(1, "vendedor1", null, null,null)),
                    List.of(new User(1, "vendedor1", null, null,null)),
                    null);

    @Test
    @DisplayName("T-0002 - Verificar que el usuario a dejar de seguir exista.(US-0007) - OK")
    public void test_unfollow_should_return_true() {
        // Arrange
        int userId = user.getUserId();
        int unfollowUserId = unfollowUser.getUserId();

        // Simula encontrar ambos usuarios
        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.findUserById(unfollowUserId)).thenReturn(unfollowUser);

        // Act
        boolean result = userService.unfollow(userId, unfollowUserId);

        // Assert
        assertTrue(result);
        // Verifica que se llamó una vez
        Mockito.verify(userRepository, times(1)).unfollow(user, unfollowUser);
    }

    @Test
    @DisplayName("T-0002 - Verificar que el usuario a dejar de seguir exista.(US-0007) - ERROR")
    public void test_unfollow_should_throw_user_doesnt_exist() {
        // Arrange
        int userId = user.getUserId();
        int unfollowUserId = 99;

        // Simula encontrar solo al usuario principal
        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.findUserById(unfollowUserId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.unfollow(userId, unfollowUserId);
        });

        assertEquals("The user doesnt exist", exception.getMessage());
        // Verifica que no se llamó una vez
        Mockito.verify(userRepository, times(0)).unfollow(user, unfollowUser);
    }

    @Test
    @DisplayName("T-0003 - Method followersOrderBy should get an ascending alphabetical sorted list of users when 'name_asc' request param is provided")
    void test_followersOrderBy_should_get_an_ascending_alphabetical_sorted_user_list() {
        String sortingParam = "name_asc";
        int userId = 1;
        User user = getVendedor1();
        int expectedFollowersListSize = 3;
        List<User> expectedFollowersList = getVendedor1FollowersAsc();

        when(userRepository.findUserById(userId)).thenReturn(user);
        when(userRepository.findFollowersOrderedByName(userId, sortingParam)).thenReturn(expectedFollowersList);

        FollowersListDTO obtainedVendedor1Followers = userService.followersOrderBy(userId, sortingParam);

        verify(userRepository, atLeastOnce()).findUserById(userId);
        verify(userRepository, atLeastOnce()).findFollowersOrderedByName(userId, sortingParam);
        assertEquals(expectedFollowersListSize, obtainedVendedor1Followers.getFollowers().size());
    }

    @Test
    @DisplayName("T-0003 - Method followersOrderBy should get an ascending alphabetical sorted list of users when 'name_desc' request param is provided")
    void test_followersOrderBy_should_get_an_descending_alphabetical_sorted_user_list() {
        String sortingParam = "name_desc";
        int userId = 1;
        User user = getVendedor1();
        int expectedFollowersListSize = 3;
        List<User> expectedFollowersList = getVendedor1FollowersDesc();

        when(userRepository.findUserById(userId)).thenReturn(user);
        when(userRepository.findFollowersOrderedByName(userId, sortingParam)).thenReturn(expectedFollowersList);

        FollowersListDTO obtainedVendedor1Followers = userService.followersOrderBy(userId, sortingParam);

        verify(userRepository, atLeastOnce()).findUserById(userId);
        verify(userRepository, atLeastOnce()).findFollowersOrderedByName(userId, sortingParam);
        assertEquals(expectedFollowersListSize, obtainedVendedor1Followers.getFollowers().size());
    }
    @Test
    @DisplayName("T-0003 - Method followersOrderBy should get an ascending alphabetical sorted list of users when sorting request param is null")
    void test_followersOrderBy_should_get_an_ascending_alphabetical_sorted_user_list_when_param_is_null() {
        String sortingParam = null;
        int userId = 1;
        User user = getVendedor1();
        int expectedFollowersListSize = 3;

        when(userRepository.findUserById(userId)).thenReturn(user);

        FollowersListDTO obtainedVendedor1Followers = userService.followersOrderBy(userId, sortingParam);

        verify(userRepository, atLeastOnce()).findUserById(userId);
        assertEquals(expectedFollowersListSize, obtainedVendedor1Followers.getFollowers().size());
    }

    @Test
    @DisplayName("T-0003 - Method followersOrderBy should throw a NotFoundException if user id is not correct")
    void test_followersOrderBy_should_throw_a_UserNotFoundException_when_id_is_not_OK() {
        String sortingParam = "name_asc";
        int userId = 120;

        when(userRepository.findUserById(userId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.followersOrderBy(userId, sortingParam));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("T-0003 - Method followersOrderBy should throw a BadRequestException if user order query param is not valid")
    void test_followersOrderBy_should_throw_a_BadRequestException_when_order_is_null() {
        String sortingParam = "ksjdksdjks";
        int userId = 1;

        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.followersOrderBy(userId, sortingParam));
        assertEquals("The provided filter param is not valid", exception.getMessage());
    }

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
    @DisplayName("T-0004 - Method followersOrderBy should perform a descending alphabetical sorting when 'name_desc' request param is provided")
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