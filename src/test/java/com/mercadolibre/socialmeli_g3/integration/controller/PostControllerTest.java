package com.mercadolibre.socialmeli_g3.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mercadolibre.socialmeli_g3.dto.ProductDTO;
import com.mercadolibre.socialmeli_g3.dto.ProductPostDTO;
import com.mercadolibre.socialmeli_g3.dto.response.*;
import com.mercadolibre.socialmeli_g3.dto.PromoProductPostDTO;
import com.mercadolibre.socialmeli_g3.dto.response.ExceptionDTO;
import com.mercadolibre.socialmeli_g3.dto.response.FindProductsPromoResponseDTO;
import com.mercadolibre.socialmeli_g3.dto.response.MessageDTO;
import com.mercadolibre.socialmeli_g3.dto.response.PostDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import java.util.Arrays;

import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private static final ProductPostDTO post200 = getPost200();
    private static final ProductPostDTO post400 = getPost400();
    private static final ProductPostDTO post404 = getPost404();
    private static final PromoProductPostDTO promoPost200 = getPromoPost200();
    private static final PromoProductPostDTO promoPost400 = getPromoPost400();
    private static final PromoProductPostDTO promoPost404 = getPromoPost404();
    private static final PostDTO makePromo200 = getMakePromo200();
    private static final PostDTO makePromo400 = getMakePromo400();

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
    @DisplayName("IT-0005 - The endpoint /products/post should return a correctly message when the post is created")
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
            // Expected (400 hay varios error messages pero probe uno solo)
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(new ExceptionDTO("Post already exists for this user and product")));
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
    @DisplayName("IT-0005 - The endpoint /products/post should return a 404 error when the product does not exist")
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

    @DisplayName("IT-00010 - The endpoint /products/promo-post should return a message when the promo post is created")
    public void test_createPromoPost_should_return_200() throws Exception {
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String body = writer.writeValueAsString(promoPost200);
        // Expected
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(new MessageDTO("Post Promo created successfully")));
        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");

        // ACT & ASSERT
        mockMvc.perform(post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-00010 - The endpoint /products/promo-post should return a 400 error when the request is invalid")
    public void test_createPromoPost_should_return_400() throws Exception {
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String body = writer.writeValueAsString(promoPost400);
        // Expected (400 hay varios error messages pero probe uno solo)
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(new ExceptionDTO("Discount must be between 0 and 1")));
        ResultMatcher expectedStatusCode = status().isBadRequest();
        ResultMatcher expectedContentType = content().contentType("application/json");

        // ACT & ASSERT
        mockMvc.perform(post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-00010 - The endpoint /products/promo-post should return a 404 error when the product does not exist")
    public void test_createPromoPost_should_return_404() throws Exception {
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String body = writer.writeValueAsString(promoPost404);
        // Expected
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(new ExceptionDTO("Product Not Found")));
        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType("application/json");

        // ACT & ASSERT
        mockMvc.perform(post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0013 - The endpoint /products/promo-post/list should return PromoProductPostListDTO successfully")
    public void test_makePostAPromo_should_return_200() throws Exception {
        // Expected
        ResultMatcher expectedStatusCode = status().isOk();
        ResultMatcher expectedContentType = content().contentType("application/json");

        PromoProductPostDTO makePromoResponse = mapper.convertValue(makePromo200, PromoProductPostDTO.class);
        makePromoResponse.setHasPromo(true);
        makePromoResponse.setDiscount(0.75);
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(makePromoResponse));

        // ACT & ASSERT
        mockMvc.perform(put("/products/post/{postId}", makePromo200.getPostId())
                        .param("discount", "0.75"))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0013 - The endpoint /products/promo-post/list should return 400 when the post is already a promo post")
    public void test_makePostAPromo_should_return_400() throws Exception {
        // Expected
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(new ExceptionDTO("Post is already a promo post")));
        ResultMatcher expectedStatusCode = status().isBadRequest();
        ResultMatcher expectedContentType = content().contentType("application/json");

        // ACT & ASSERT
        mockMvc.perform(put("/products/post/{postId}", makePromo400.getPostId())
                        .param("discount", "0.25"))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0013 - The endpoint /products/promo-post/list should return 404 when the post does not exist")
    public void test_makePostAPromo_should_return_404() throws Exception {
        // Arrange
        int postId = 999;
        ResultMatcher expectedBody = content().json(mapper.writeValueAsString(new ExceptionDTO("Post not found")));
        ResultMatcher expectedStatusCode = status().isNotFound();
        ResultMatcher expectedContentType = content().contentType("application/json");

        // ACT & ASSERT
        mockMvc.perform(put("/products/post/{postId}", postId)
                        .param("discount", "0.25"))
                .andExpectAll(expectedStatusCode, expectedContentType, expectedBody)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-0006 - The endpoint /products/filter should return a list of posts successfully")
    public void findProductByIdUser_ok() throws Exception {

        ResultMatcher status = status().isOk();
        ResultMatcher contentType = content().contentType("application/json");
        ResultMatcher bodyContent = content().json(mapper.writeValueAsString(getProductByIdUserResponseDTO()));

        int userIdSearch = 1;

        mockMvc.perform(get("/products/followed/{userId}/list",userIdSearch))
                .andExpect(status)
                .andExpect(contentType)
                .andExpect(bodyContent)
                .andDo(print());

    }

    //US009 date_asc
    @Test
    @DisplayName("IT-0009 - The endpoint /products/followed/{userId}/list should return a list of products ordered by date ascendant")
    public void findProductByIdUser_order_date_asc_ok() throws Exception {

        ResultMatcher status = status().isOk();
        ResultMatcher contentType = content().contentType("application/json");
        ResultMatcher bodyContent = content().json(mapper.writeValueAsString(getProductByIdUserResponseDTO()));

        int userIdSearch = 1;

        mockMvc.perform(get("/products/followed/{userId}/list?order=date_asc",userIdSearch))
                .andExpect(status)
                .andExpect(contentType)
                .andExpect(bodyContent)
                .andDo(print());

    }

    //US009 date_desc
    @Test
    @DisplayName("IT-0009 - The endpoint /products/followed/{userId}/list should return a list of products ordered by date descendant")
    public void findProductByIdUser_order_date_desc_ok() throws Exception {

        ResultMatcher status = status().isOk();
        ResultMatcher contentType = content().contentType("application/json");
        ResultMatcher bodyContent = content().json(mapper.writeValueAsString(getproductByIdUserResponseDTOByOrderDesc()));

        int userIdSearch = 1;

        mockMvc.perform(get("/products/followed/{userId}/list?order=date_desc",userIdSearch))
                .andExpect(status)
                .andExpect(contentType)
                .andExpect(bodyContent)
                .andDo(print());

    }
}
