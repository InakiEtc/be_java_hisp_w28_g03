package com.mercadolibre.socialmeli_g3.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mercadolibre.socialmeli_g3.dto.ProductDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.dto.response.ExceptionDTO;
import com.mercadolibre.socialmeli_g3.dto.response.FindProductsPromoResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.response.MessageDTO;
import com.mercadolibre.socialmeli_g3.dto.response.PostDTO;
import com.mercadolibre.socialmeli_g3.repository.filters.BrandFilter;
import com.mercadolibre.socialmeli_g3.repository.filters.ColorFilter;
import com.mercadolibre.socialmeli_g3.repository.filters.ProductNameFilter;
import com.mercadolibre.socialmeli_g3.repository.filters.TypeFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getListPostDTO;
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
    @DisplayName("IT-0017 - The endpoint /products/posts/by-product-attributes/ should return filtered posts successfully")
    public void should_return_a_list_of_PostDTO_filtered_by_productName_brand_color_or_type_successfully() throws Exception {
        Map<String, String> params = Map.of(
                "product_name", "Silla",
                "brand", "Racer",
                "type", "gamer",
                "color", "Red"
        );
        List<PostDTO> expectedPosts = getListPostDTO();
        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedPosts));

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(params);

        mockMvc.perform(get("/products/posts/by-product-attributes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0017 - The endpoint /products/posts/by-product-attributes/ should throw a NotFoundException when no posts are found with the given params")
    public void should_throw_a_NotFoundException_when_no_posts_are_found() throws Exception {
        Map<String, String> params = Map.of(
                "product_name", "pepe",
                "brand", "Racer",
                "type", "gamer",
                "color", "Red"
        );
        ExceptionDTO expectedException = new ExceptionDTO("No posts have been found with the provided filters");
        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType("application/json");
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(expectedException));

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(params);

        mockMvc.perform(get("/products/posts/by-product-attributes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }
}
