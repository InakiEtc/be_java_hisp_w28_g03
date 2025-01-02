package com.mercadolibre.socialmeli_g3.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.response.FindProductsPromoResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerIntegration {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    private static final FindProductsPromoResponseDTO promoPost = new FindProductsPromoResponseDTO(
            1,
            "vendedor1",
            3
    );

    //US-11
    @Test
    @DisplayName("findProductsPromoCount amount of promo ok")
    public void findProductsPromoCount_amount_promo_ok() throws Exception {
        ResultMatcher status = status().isOk();
        ResultMatcher contentType = content().contentType("application/json");
        ResultMatcher bodyContent = content().json(objectMapper.writeValueAsString(promoPost));

        int userIdSearch = 1;

        mockMvc.perform(get("/products/promo-post/count").
                        param("user_id", String.valueOf(userIdSearch)))
                .andExpect(status)
                .andExpect(contentType)
                .andExpect(bodyContent)
                .andDo(print());

    }
}
