package com.mercadolibre.socialmeli_g3.unit.repository.filters;

import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import com.mercadolibre.socialmeli_g3.repository.filters.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class FilterFactoryTest {

    @Autowired
    private FilterFactory factory;

    @Test
    @DisplayName("T-00017 - getFilter_should_create_and_return_a_filter_class_successfully")
    void getFilterShouldCreateAndReturnAFilterClassSuccessfully() {
        Map<String, String> params = Map.of(
                "product_name", "",
                "brand", "",
                "type", "",
                "color", ""
        );
    }
}