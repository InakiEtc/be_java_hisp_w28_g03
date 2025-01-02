package com.mercadolibre.socialmeli_g3.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductDTO;
import com.mercadolibre.socialmeli_g3.dto.response.ExceptionDTO;
import com.mercadolibre.socialmeli_g3.dto.response.MessageDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private static final ProductPostDTO post200 = new ProductPostDTO(
            3, "29-04-2021",
            new ProductDTO(104, "Monitor Gamer", "Gamer", "AOC", "Black", "Curved"),
            100, 1500.50);

    private static final ProductPostDTO post400 = new ProductPostDTO(
            2, "29-04-2021",
            new ProductDTO(104, "Monitor Gamer", "Gamer", "AOC", "Black", "Curved"),
            100, 1500.50);

    private static final ProductPostDTO post404 = new ProductPostDTO(
            3, "29-04-2021",
            new ProductDTO(999,"Fake Product", "Fake Category", "Fake Brand", "Fake Color", "Fake Model"),
            100, 1500.50);


    @Test
    @DisplayName("IT-0005 - The endpoint /products/post should return a ProductPostDTO and correctly log a post")
    public void test_createPost_should_return_200() throws Exception {
        // Arrange
            // Entry
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String body = writer.writeValueAsString(post200);
            // Expected
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(new MessageDTO("Post created successfully")));
        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");


        // ACT & ASSERT
        mockMvc.perform(post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0005 - The endpoint /products/post should return a 400 error when the request is invalid")
    public void test_createPost_should_return_400() throws Exception {
        // Arrange
            // Entry
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String body = writer.writeValueAsString(post400);
            // Expected (404 hay varios error messages pero probe uno solo)
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(new MessageDTO("Post already exists for this user and product")));
        ResultMatcher expectedStatusCode = status().isBadRequest();
        ResultMatcher expectedContentType = content().contentType("application/json");


        // ACT & ASSERT
        mockMvc.perform(post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0005 - The endpoint /products/post should return a 404 error when the user does not exist")
    public void test_createPost_should_return_404() throws Exception {
        // Arrange
            // Entry
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String body = writer.writeValueAsString(post404);
            // Expected
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(new ExceptionDTO("Product Not Found")));
        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType("application/json");


        // ACT & ASSERT
        mockMvc.perform(post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    public void test_createPromoPost_should_return_200() throws Exception {
    }

    @Test
    public void test_createPromoPost_should_return_400() throws Exception {
    }

    @Test
    public void test_createPromoPost_should_return_404() throws Exception {
    }
}