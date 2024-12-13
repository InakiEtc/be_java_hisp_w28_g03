package com.mercadolibre.socialmeli_g3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String userName;
    private List<User> followers;
    private List<User> followed;
    private List<Post> posts;
}
