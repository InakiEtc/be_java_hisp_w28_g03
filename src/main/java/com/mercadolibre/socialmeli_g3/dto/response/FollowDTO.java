package com.mercadolibre.socialmeli_g3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowDTO {
    private int userId;
    private int follower;
}
