package com.mercadolibre.socialmeli_g3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.FollowedListDTO;
import com.mercadolibre.socialmeli_g3.dto.FollowersListDTO;
import com.mercadolibre.socialmeli_g3.dto.UserDTO;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.exception.InvalidOperationException;
import com.mercadolibre.socialmeli_g3.exception.NotFoundException;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FollowersListDTO getSellerFollowers(int userId) {

        User userFollowers = userRepository.getFollowers(userId);
        if (userFollowers == null) throw new NotFoundException("El usuario no existe");

        List<UserDTO> followersList = userFollowers.getFollowers().stream()
                .map(user -> new UserDTO(user.getUserId(), user.getUserName()))
                .collect(Collectors.toList());

        return new FollowersListDTO(userFollowers.getUserId(), userFollowers.getUserName(), followersList);
    }

    @Override
    public void unfollow(int userId, int userIdToUnfollow) {
        if (userId == userIdToUnfollow) throw new InvalidOperationException("No puedes dejar de seguirte a ti mismo");

        User user = userRepository.findUserById(userId);
        if (user == null) throw new NotFoundException("El usuario no existe");
        User userToUnfollow = userRepository.findUserById(userIdToUnfollow);
        if (userToUnfollow == null) throw new NotFoundException("El usuario no existe");

        if (!user.getFollowed().contains(userToUnfollow) || !userToUnfollow.getFollowers().contains(user)) {
            throw new NotFoundException("El usuario no esta en la lista de seguidos");
        }

        userRepository.unfollow(user, userToUnfollow);
    }
}
