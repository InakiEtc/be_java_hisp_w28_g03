package com.mercadolibre.socialmeli_g3.unit.repository;

import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getVendedor1FollowersAsc;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryImplTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    @DisplayName("Buscar usuario por ID - OK")
    public void test_findUserById_should_return_user() {
        // Arrange
        int userId = 1;
        String userName = "vendedor1";
        // Act
        User result = userRepository.findUserById(userId);
        // Assert
        assertNotNull(result);
        assertEquals(userName, result.getUserName());
    }

    @Test
    @DisplayName("Buscar usuario por ID - ERROR")
    public void test_findUserById_should_return_user_not_found() {
        // Arrange
        int userId = 99;
        // Act
        User result = userRepository.findUserById(userId);
        // Assert
        assertNull(result, "User not found");
    }

    @Test
    public void findFollowersOrderedByName_should_perform_ascending_alphabetical_sorting() {
        String sortingParam = "name_asc";
        int userId = 1;
        List<User> expectedFollowersList = getVendedor1FollowersAsc();

    }
}