package com.mercadolibre.socialmeli_g3.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mercadolibre.socialmeli_g3.dto.ProductDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.dto.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

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


    private static final FindProductsPromoResponseDTO promoPost = new FindProductsPromoResponseDTO(
            1,
            "vendedor1",
            3
    );

    //US-11
    @Test
    @DisplayName("IT-00011 - The endpoint /products/promo-post/count should return a PromoPostDTO successfully")
    public void findProductsPromoCount_amount_promo_ok() throws Exception {
        ResultMatcher status = status().isOk();
        ResultMatcher contentType = content().contentType("application/json");
        ResultMatcher bodyContent = content().json(mapper.writeValueAsString(promoPost));

        int userIdSearch = 1;

        mockMvc.perform(get("/products/promo-post/count").
                        param("user_id", String.valueOf(userIdSearch)))
                .andExpect(status)
                .andExpect(contentType)
                .andExpect(bodyContent)
                .andDo(print());

    }

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
    @DisplayName("T-0012 Find products on promo by user should return PromoProductPostListDTO")
    void test_findProductsOnPromoByUser_should_return_PromoProductPostListDTO() throws Exception {
        String userId = "1";
        PromoProductPostListDTO responseWaited = new PromoProductPostListDTO(1, "vendedor1",
                Arrays.asList(
                        new PostDTO(201, 1, "20-12-2024",
                                new ProductDTO(101, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition"),
                                100, 1500.50, true, 0.40),

                        new PostDTO(202, 1, "21-11-2024",
                                new ProductDTO(102, "Teclado Mecánico", "Teclado", "Logitech", "Black", "RGB Backlit"),
                                58, 250.00, true, 0.30),

                        new PostDTO(203, 1, "03-08-2023",
                                new ProductDTO(103, "Mouse Gamer", "Gamer", "Razer", "Green", "Wireless"),
                                60, 120.00, true, 0.25)
                ));

        ResultMatcher expectedBody = content().json((mapper.writeValueAsString(responseWaited)));
        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(get("/products/promo-post/list")
                        .param("user_id", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(expectedContentType)
                .andExpect(expectedBody)
                .andExpect(expectedStatusCode)
                .andDo(print());
    }


    @Test
    @DisplayName("T-0012 Find products on promo by user should return NotFoundException")
    void test_findProductsOnPromoByUser_should_throw_error() throws Exception {
        String userId = "100";

        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(new ExceptionDTO("User not found")));
        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(get("/products/promo-post/list")
                        .param("user_id", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(expectedContentType)
                .andExpect(expectedBody)
                .andExpect(expectedStatusCode)
                .andDo(print());
    }
}
