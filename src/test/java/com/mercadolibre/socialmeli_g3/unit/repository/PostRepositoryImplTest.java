package com.mercadolibre.socialmeli_g3.unit.repository;


import com.mercadolibre.socialmeli_g3.entity.Post;
import com.mercadolibre.socialmeli_g3.entity.Product;
import com.mercadolibre.socialmeli_g3.repository.PostRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getPost;
import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getPost2;
import static com.mercadolibre.socialmeli_g3.utils.TestDataFactory.getPost3;
@SpringBootTest
public class PostRepositoryImplTest {



    @Autowired
    private PostRepositoryImpl repository;

    @Test
    @DisplayName("T-0008 - findProductByIdUser should return list empty when product  is before than from last 2 weeks")
    public void findProductByIdUser_should_return_list_empty_when_product_is_before_than_from_last_2_weeks(){
        //arrange
        List<Post> listExpect = new ArrayList<>();
        Post post = getPost();
        post.setUserId(1005);
        int param = post.getUserId();
        post.setDate(getDate(15));
        //act
        if(repository.findProductByIdUser(param).size() == 0){
            repository.createPost(post);
        }
        List<Post> listResult = repository.findProductByIdUser(param);
        //assert
        Assertions.assertEquals(listResult,listExpect);
    }

    @Test
    @DisplayName("T-0008 - findProductByIdUser with 3 is function that should return in this case 3 products list of product when product is from last 2 weeks")
    public void findProductByIdUser_with_3_products_should_return_list_product_when_product_from_last_2_weeks(){
        //arrange
        Post post = getPost();
        Post post2 = getPost2();
        Post post3 = getPost3();
        int param = post.getUserId();
        post.setDate(getDate(1));
        post2.setDate(getDate(2));
        post3.setDate(getDate(3));
        if(repository.findProductByIdUser(param).size() == 0){
            repository.createPost(post);
            repository.createPost(post2);
            repository.createPost(post3);
        }
        List<Post> listExpected = new ArrayList<>(Arrays.asList(post,post2,post3));

        //act
        List<Post> listResult = repository.findProductByIdUser(param);
        //assert
        Assertions.assertNotNull(listResult);
        Assertions.assertEquals(listResult,listExpected);
    }

    @Test
    @DisplayName("T-0008 - findProductByIdUser Order Last datefirst with 3 is function that should return in this case 3 products list of product when product is from last 2 weeks")
    public void findProductByIdUser_order_Last_date_first_with_3_products_should_return_list_product_when_product_from_last_2_weeks(){
        //arrange
        Post post = getPost();
        Post post2 = getPost2();
        Post post3 = getPost3();
        int param = post.getUserId();
        post.setDate(getDate(6));
        post2.setDate(getDate(3));
        post3.setDate(getDate(1));
        if(repository.findProductByIdUser(param).size() == 0){
            repository.createPost(post);
            repository.createPost(post2);
            repository.createPost(post3);
        }
        List<Post> listOrderedExpected = new ArrayList<>(Arrays.asList(post3,post2,post));

        //act
        List<Post> listResult = repository.findProductByIdUser(param);
        //assert
        Assertions.assertNotNull(listResult);
        Assertions.assertEquals(listResult,listOrderedExpected);
    }
    public static String getDate(Integer subtractDays) {
        //Obtiene la fecha
        LocalDate dateTimeNow = LocalDate.now();
        //Validamos los dias que se quieran restar
        if (subtractDays != null && subtractDays > 0) {
            dateTimeNow = dateTimeNow.minusDays(subtractDays);
        }
        //Retornamos con el formato adecuado
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTimeNow.format(formateador);
    }
}
