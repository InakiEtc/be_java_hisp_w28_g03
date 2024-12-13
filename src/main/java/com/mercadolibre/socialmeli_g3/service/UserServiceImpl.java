package com.mercadolibre.socialmeli_g3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.FollowedPostsDTO;
import com.mercadolibre.socialmeli_g3.dto.FollowersCountDTO;
import com.mercadolibre.socialmeli_g3.dto.FollowersListDTO;
import com.mercadolibre.socialmeli_g3.dto.UserDTO;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.socialmeli_g3.dto.FollowedListDTO;
import com.mercadolibre.socialmeli_g3.dto.UserDTO;
import com.mercadolibre.socialmeli_g3.dto.response.FollowDTO;
import com.mercadolibre.socialmeli_g3.entity.User;
import com.mercadolibre.socialmeli_g3.exception.BadRequestException;
import com.mercadolibre.socialmeli_g3.exception.ConflictException;
import com.mercadolibre.socialmeli_g3.dto.FollowersListDTO;
import com.mercadolibre.socialmeli_g3.exception.InvalidOperationException;
import com.mercadolibre.socialmeli_g3.exception.NotFoundException;
import com.mercadolibre.socialmeli_g3.repository.IUserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public FollowedListDTO getFollowedByUserId(int id) {
        User user = userRepository.findUserById(id);
        if (user == null) throw new NotFoundException("El usuario no existe");
        FollowedListDTO followedListDTO = new FollowedListDTO();

        List<UserDTO> followedUsersDTOS = user.getFollowed()
                                                .stream().map(u -> new UserDTO(u.getUserId(), u.getUserName()))
                                                .toList();
//        @Test manual prueba de error
//        List<UserDTO> followedUsersDTOS= new ArrayList<>();

        if (followedUsersDTOS == null || followedUsersDTOS.isEmpty()) {
            throw new NotFoundException("El usuario " + user.getUserName() + " no sigue a ningun vendedor");
        }

        followedListDTO.setUserId(user.getUserId());
        followedListDTO.setUserName(user.getUserName());
        followedListDTO.setFollowed(followedUsersDTOS);
        return followedListDTO;
    }

    @Override
    public FollowersListDTO getSellerFollowers(int userId) {

        User userFollowers = userRepository.getFollowers(userId);
        if (userFollowers == null) throw new NotFoundException("El usuario no existe");

        List<UserDTO> followersList = userFollowers.getFollowers().stream()
                .map(user -> new UserDTO(user.getUserId(), user.getUserName()))
                .toList();

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

    @Override
    public FollowDTO follow(int userId, int userIdToFollow) {
        if (userId <= 0) {
            throw new BadRequestException("El id del seguidor no es valido");
        }
        if(userIdToFollow <= 0) {
            throw new BadRequestException("El id de usuario a seguir no es valido");
        }

        User user = userRepository.findUserById(userId);
        if (user == null) throw new NotFoundException("El usuario no existe");
        User userToFollow = userRepository.findUserById(userIdToFollow);
        if (userToFollow == null) throw new NotFoundException("El usuario no existe");

        if (userId == userIdToFollow) throw new InvalidOperationException("No puedes seguirte a ti mismo");

        if (user.getFollowed().contains(userToFollow) || userToFollow.getFollowers().contains(user)) {
            throw new ConflictException("El usuario ya esta en la lista de seguidos");
        }

        userRepository.follow(user, userToFollow);
        return new FollowDTO(userId, userIdToFollow);
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
