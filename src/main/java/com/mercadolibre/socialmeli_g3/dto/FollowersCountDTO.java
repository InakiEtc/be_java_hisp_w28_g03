package com.mercadolibre.socialmeli_g3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowersCountDTO {
        private int userId;
        private String userName;
        private int followersCount;
}
