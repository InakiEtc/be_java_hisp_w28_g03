package com.mercadolibre.socialmeli_g3.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.response.ExceptionDTO;
import com.mercadolibre.socialmeli_g3.dto.response.FollowDTO;
import com.mercadolibre.socialmeli_g3.dto.response.FollowersListDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getVendedor1FollowDTOUser6;
import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getVendedor1FollowersListDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("IT-0001 - The endpoint /{userId}/follow/{userIdToFollow} should return a FollowDTO and correctly log a follow relationship")
    void should_complete_a_follow() throws Exception {
        int userId = 1;
        int userIdToFollow = 6;
        FollowDTO followDTO = getVendedor1FollowDTOUser6();

        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(followDTO));

        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", userId, userIdToFollow)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0001 - The endpoint /{userId}/follow/{userIdToFollow} should throw a BadRequestException when userId is not positive or userIdTofollow is not positive")
    void should_throw_a_BadRequestException_when_userId_is_null() throws Exception {
        int userId = -1;
        int userIdToFollow = 6;
        List<String> errorMessage = new ArrayList<>(List.of("userId: The user id must be a positive number"));
        String errorDetails = errorMessage.toString();

        ExceptionDTO expectedException = new ExceptionDTO("Data request invalid", errorDetails);

        ResultMatcher expectedStatusCode = status().isBadRequest();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", userId, userIdToFollow)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0001 - The endpoint /{userId}/follow/{userIdToFollow} should throw a ConflictException when userId equals to userIdToFollow")
    void should_throw_a_ConflictException_when_userId_equals_to_userIdToFollow() throws Exception {
        int userId = 1;
        int userIdToFollow = 1;
        String errorMessage = "You cant follow yourself";

        ExceptionDTO expectedException = new ExceptionDTO(errorMessage);

        ResultMatcher expectedStatusCode = status().isConflict();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", userId, userIdToFollow)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0001 - The endpoint /{userId}/follow/{userIdToFollow} should throw a NotFoundException when userId is not found")
    void should_throw_a_NotFoundException_when_userId_is_not_found() throws Exception {
        int userId = 120;
        int userIdToFollow = 1;
        String errorMessage = "User not found";

        ExceptionDTO expectedException = new ExceptionDTO(errorMessage);

        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}", userId, userIdToFollow)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0003 - The endpoint /users/{userId}/followers/list should return a FollowersListDTO of an given user")
    void should_return_a_followersListDTO_of_an_given_user() throws Exception {
        int userId = 1;
        FollowersListDTO expectedFollowersListDTO = getVendedor1FollowersListDTO();

        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedFollowersListDTO));

        mockMvc.perform(get("/users/{userId}/followers/list", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0003 - The endpoint /users/{userId}/followers/list should throw a NotFoundException when userId is not positive")
    void should_throw_a_NotFoundException_when_userId_is_null() throws Exception {

        int userId = -1;
        List<String> errorMessage = new ArrayList<>(List.of("userId: The user id must be a positive number"));
        String errorDetails = errorMessage.toString();

        ExceptionDTO expectedException = new ExceptionDTO("Data request invalid", errorDetails);

        ResultMatcher expectedStatusCode = status().isBadRequest();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(get("/users/{userId}/followers/list", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());

    }

    @Test
    @DisplayName("IT-0003 - The endpoint /users/{userId}/followers/list should throw a NotFoundException when userId is not found")
    void should_throw_a_NotFoundException_when_userId_is_not_found_on_followers_list() throws Exception {
        int userId = 120;

        ExceptionDTO expectedException = new ExceptionDTO("User not found", null);

        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(get("/users/{userId}/followers/list", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0007 - The endpoint /{userId}/unfollow/{userIdToUnfollow} should return a 204 NO_CONTENT when unfollow is successful")
    void should_return_a_204_NO_CONTENT_when_unfollow_is_successful() throws Exception {
        int userId = 1;
        int userIdToUnfollow = 2;

        ResultMatcher expectedStatusCode = status().isNoContent();

        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, userIdToUnfollow)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0007 - The endpoint /{userId}/unfollow/{userIdToUnfollow} should throw a NotFoundException when userId is not positive")
    void should_throw_a_NotFoundException_when_userId_is_negative_unfollowing() throws Exception {

        int userId = -1;
        int userIdToUnfollow = 2;
        List<String> errorMessage = new ArrayList<>(List.of("userId: The user id must be a positive number"));
        String errorDetails = errorMessage.toString();

        ExceptionDTO expectedException = new ExceptionDTO("Data request invalid", errorDetails);

        ResultMatcher expectedStatusCode = status().isBadRequest();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, userIdToUnfollow)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());

    }

    @Test
    @DisplayName("IT-0007 - The endpoint /{userId}/unfollow/{userIdToUnfollow} should throw a NotFoundException when userIdToUnfollow is not positive")
    void should_throw_a_NotFoundException_when_userIdToUnfollow_is_negative_unfollowing() throws Exception {

        int userId = 1;
        int userIdToUnfollow = -2;
        List<String> errorMessage = new ArrayList<>(List.of("userIdToUnfollow: The user id to unfollow must be a positive number"));
        String errorDetails = errorMessage.toString();

        ExceptionDTO expectedException = new ExceptionDTO("Data request invalid", errorDetails);

        ResultMatcher expectedStatusCode = status().isBadRequest();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, userIdToUnfollow)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());

    }

    @Test
    @DisplayName("IT-0007 - The endpoint /{userId}/unfollow/{userIdToUnfollow} should throw a NotFoundException when userId is not found")
    void should_throw_a_NotFoundException_when_userId_is_not_found_unfollowing() throws Exception {
        int userId = 120;
        int userIdToUnfollow = 2;
        String errorMessage = "User not found";
        ExceptionDTO expectedException = new ExceptionDTO(errorMessage, null);

        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, userIdToUnfollow)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0007 - The endpoint /{userId}/unfollow/{userIdToUnfollow} should throw a NotFoundException when userIdToUnfollow is not found")
    void should_throw_a_NotFoundException_when_userIdToUnfollow_is_not_found_unfollowing() throws Exception {
        int userId = 1;
        int userIdToUnfollow = 120;
        String errorMessage = "User not found";
        ExceptionDTO expectedException = new ExceptionDTO(errorMessage, null);

        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, userIdToUnfollow)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0007 - The endpoint /{userId}/unfollow/{userIdToUnfollow} should throw a BadRequestException when userId equals to userIdToUnfollow")
    void should_throw_a_BadRequestException_when_userId_equals_to_userIdToUnfollow() throws Exception {
        int userId = 1;
        int userIdToUnfollow = 1;
        String errorMessage = "You cannot unfollow yourself";

        ExceptionDTO expectedException = new ExceptionDTO(errorMessage, null);

        ResultMatcher expectedStatusCode = status().isBadRequest();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, userIdToUnfollow)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0007 - The endpoint /{userId}/unfollow/{userIdToUnfollow} should throw a NotFoundException when userIdToUnfollow is not a follower of userId")
    void should_throw_a_NotFoundException_when_userIdToUnfollow_is_not_a_follower_of_userId() throws Exception {
        int userId = 1;
        int userIdToUnfollow = 3;
        String errorMessage = "The user is not in the following list";
        ExceptionDTO expectedException = new ExceptionDTO(errorMessage, null);

        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, userIdToUnfollow)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

}