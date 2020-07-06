package br.com.service.store.services;

import br.com.service.store.dtos.UserDTO;
import br.com.service.store.exceptions.FaultException;
import br.com.service.store.models.User;
import br.com.service.store.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public @Autowired UserRepository userRepository;


    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        UserDTO userDTO = UserDTO.fromModel(user);
        return userDTO;
    }

    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id).orElse(null);
        UserDTO userDTO = UserDTO.fromModel(user);
        return userDTO;
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user =  userRepository.save(UserDTO.toModel(userDTO));
        return UserDTO.fromModel(user);
    }

    public UserDTO deletUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new FaultException(HttpStatus.UNPROCESSABLE_ENTITY, "User not found."));
        userRepository.delete(user);
        return UserDTO.fromModel(user);
    }
}
