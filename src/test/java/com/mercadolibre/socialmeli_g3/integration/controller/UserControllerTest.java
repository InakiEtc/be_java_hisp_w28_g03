package com.mercadolibre.socialmeli_g3.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.*;
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
    @DisplayName("IT-0001 - The endpoint /{userId}/follow/{userIdToFollow} should throw a BadResquestException when userId is not positive or userIdTofollow is not positive")
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
        String errorMessage = "The user does not exist";

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
    @DisplayName("IT-0002 - The endpoint users/{userId}/followers/count should return a FollowCountDTO and StatusCode OK(200)")
    void should_countFollowers_when_ok() throws Exception {
        int userId = 1;
        FollowersCountDTO followersCountDTODTO = getFollowersCountDTO();

        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(followersCountDTODTO));

        mockMvc.perform(get("/users/{userId}/followers/count", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0002 - The endpoint users/{userId}/followers/count should return a FollowCountDTO and StatusCode Not found(400)")
    void should_countFollowers_when_idNotFound() throws Exception {
        int userId = 100;
        FollowersCountDTO followersCountDTODTO = getFollowersCountDTO();
        String errorMessage = "The user with the id " + userId + " was not founded";

        ExceptionDTO expectedException = new ExceptionDTO(errorMessage);
        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(get("/users/{userId}/followers/count", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }
    @Test
    @DisplayName("IT-0008 - The endpoint users/{userId}/followers/list/order=name_asc should return a followersListDTO and StatusCode OK(200)")
    void should_orderAscFollowersByName_when_userId_ok() throws Exception {
        int userId = 1;
        String rParam= "name_asc";

        FollowersListDTO followersListDTO = getVendedor1FollowersDTOAsc();

        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(followersListDTO));

        mockMvc.perform(get("/users/{userId}/followers/list",userId)
                        .param("order", rParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0008 - The endpoint users/{userId}/followers/list/order=name_desc should return a followersListDTO and StatusCode OK(200)")
    void should_orderDescFollowersByName_when_userId_ok() throws Exception {
        int userId = 1;
        String rParam= "name_desc";

        FollowersListDTO followersListDTO = getVendedor1FollowersDTODesc();

        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(followersListDTO));

        mockMvc.perform(get("/users/{userId}/followers/list",userId)
                        .param("order", rParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0008 - The endpoint users/{userId}/followers/list/order=name_ascdsdsd should return a followersListDTO and StatusCode error")
    void should_orderAscFollowersByName_when_nameOrder_error() throws Exception {
        int userId = 1;
        String rParam= "name_ascasas";

        String errorMessage = "The provided filter param is not valid";

        ExceptionDTO expectedException = new ExceptionDTO(errorMessage);

        ResultMatcher expectedStatusCode = status().isBadRequest();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        mockMvc.perform(get("/users/{userId}/followers/list",userId)
                        .param("order", rParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0008 - The endpoint users/{userId}/followed/list/order=name_asc should return a FollowedListDTO and StatusCode OK(200)")
    void should_orderAscFollowedByName_when_userId_ok() throws Exception {
        int userId = 1;
        String rParam= "name_asc";

        FollowedListDTO followersListDTO = getVendedor1FollowedDTOAsc();

        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(followersListDTO));

        mockMvc.perform(get("/users/{userId}/followed/list",userId)
                        .param("order", rParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }


    @Test
    @DisplayName("IT-0008 - The endpoint users/{userId}/followed/list/order=name_desc should return a FollowedListDTO and StatusCode OK(200)")
    void should_orderDescFollowedByName_when_userId_ok() throws Exception {
        int userId = 2;
        String rParam= "name_desc";

        FollowedListDTO followedListDTO = getVendedor1FollowedDTODesc();

        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(followedListDTO));

        mockMvc.perform(get("/users/{userId}/followed/list",userId)
                        .param("order", rParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }




}