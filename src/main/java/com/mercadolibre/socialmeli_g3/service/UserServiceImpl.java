package com.mercadolibre.socialmeli_g3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.FollowedPostsDTO;
import com.mercadolibre.socialmeli_g3.dto.FollowersCountDTO;
import com.mercadolibre.socialmeli_g3.dto.FollowersListDTO;
import com.mercadolibre.socialmeli_g3.dto.UserDTO;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.exception.NotFoundException;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> searchAllUser() {
        ObjectMapper mapper = new ObjectMapper();
        List<User> vehicleList = userRepository.findAllUsers();
        if(vehicleList.isEmpty()){
            throw new NotFoundException("No se encontró ningun auto en el sistema.");
        }
        return vehicleList.stream()
                .map(v -> mapper.convertValue(v,UserDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public FollowersCountDTO getNumberFollowers(int userId) {

       User user = userRepository.findUserById(userId);
       // si el usuario devuelve vacio
        if (user ==null){
            throw new NotFoundException("El usuario con ID " + userId + " no fuen encontrado.");
        }
        // Obtengo la cantidad de followers
        List<User> followers = user.getFollowers();
        int followersCount = (followers == null) ? 0 : followers.size();

        // mapeo el FollowersCountDTO
        FollowersCountDTO countDTO = new FollowersCountDTO();
        countDTO.setUserId( user.getUserId());
        countDTO.setUserName( user.getUserName());
        countDTO.setFollowersCount(followersCount);

        // Devolver el DTO de followersCountDTO
        return countDTO;
    }
}
