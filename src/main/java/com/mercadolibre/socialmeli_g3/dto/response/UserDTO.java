package com.mercadolibre.socialmeli_g3.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_name")
    private String userName;
}
