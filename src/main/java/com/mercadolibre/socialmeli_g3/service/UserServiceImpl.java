package com.mercadolibre.socialmeli_g3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.FollowedListDTO;
import com.mercadolibre.socialmeli_g3.dto.UserDTO;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.exception.NotFoundException;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    ObjectMapper mapper = new ObjectMapper();
    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FollowedListDTO getFollowedByUserId(int id) {

        Optional<User> user =userRepository.findUserById(id);
        if(user == null || user.isEmpty()){
            throw new NotFoundException("Usuario no encontrado");
        }

        FollowedListDTO followedListDTO = new FollowedListDTO();
        UserDTO userDTO = new UserDTO();

        List<UserDTO> followedUsersDTOS= user.get().getFollowed().stream().map(u->mapper.convertValue(u, UserDTO.class)).toList();

        if(followedUsersDTOS == null || followedUsersDTOS.isEmpty()){
            throw new NotFoundException("El usuario "+ userDTO.getUserName() + " no sigue a ningun vendedor");
        }

        followedListDTO.setUserId(user.get().getUserId());
        followedListDTO.setUserName(user.get().getUserName());
        followedListDTO.setFollowed(followedUsersDTOS);

        return followedListDTO;
    }
}
