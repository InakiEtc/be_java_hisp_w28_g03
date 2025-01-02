package com.mercadolibre.socialmeli_g3.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.response.*;
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

import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getProductByIdUserResponseDTO;
import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getProductsPromo;
import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getproductByIdUserResponseDTOByOrderDesc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;


    //US-11
    @Test
    @DisplayName("findProductsPromoCount amount of promo ok")
    public void findProductsPromoCount_amount_promo_ok() throws Exception {
        ResultMatcher status = status().isOk();
        ResultMatcher contentType = content().contentType("application/json");
        ResultMatcher bodyContent = content().json(mapper.writeValueAsString(getProductsPromo()));

        int userIdSearch = 1;

        mockMvc.perform(get("/products/promo-post/count").
                        param("user_id", String.valueOf(userIdSearch)))
                .andExpect(status)
                .andExpect(contentType)
                .andExpect(bodyContent)
                .andDo(print());

    }
    //US006
    @Test
    @DisplayName("findProductsPromoCount ok")
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
    @DisplayName("findProductByIdUser order date asc ok")
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
    @DisplayName("findProductByIdUser order date desc ok")
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
