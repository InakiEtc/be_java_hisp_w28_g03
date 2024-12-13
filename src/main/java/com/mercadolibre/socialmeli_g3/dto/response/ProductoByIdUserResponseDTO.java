package com.mercadolibre.socialmeli_g3.dto.response;

import com.mercadolibre.socialmeli_g3.dto.PostDTO;

import java.util.List;

public class ProductoByIdUserResponseDTO {
    private int user_id;
    private List<PostDTO> posts;

    public ProductoByIdUserResponseDTO() {
    }

    public ProductoByIdUserResponseDTO(int user_id, List<PostDTO> posts) {
        this.user_id = user_id;
        this.posts = posts;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
}
