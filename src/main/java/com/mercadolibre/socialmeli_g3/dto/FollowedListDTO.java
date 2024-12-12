package com.mercadolibre.socialmeli_g3.dto;

import com.mercadolibre.socialmeli_g3.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowedListDTO {
        private int userId;
        private String userName;
        private List<UserDTO> followed;
}
