package com.mercadolibre.socialmeli_g3.unit.repository;

import com.mercadolibre.socialmeli_g3.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getVendedor1FollowersAsc;

@SpringBootTest
public class UserRepositoryImplTest {

    @Test
    public void findFollowersOrderedByName_should_perform_ascending_alphabetical_sorting() {
        String sortingParam = "name_asc";
        int userId = 1;
        List<User> expectedFollowersList = getVendedor1FollowersAsc();

    }
}
