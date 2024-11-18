package com.hub.deepvision.service;

import com.hub.deepvision.model.User;
import com.hub.deepvision.model.dto.AddUserDTO;
import com.hub.deepvision.model.dto.UserDTO;
import com.hub.deepvision.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(AddUserDTO user) {
        if (userRepository.existsByName(user.getEmail())) {
            throw new DuplicateKeyException("User with this email already exists");
        }
        return userRepository.save(modelMapper.map(user, User.class));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with this id"));
    }


    public User updateUserById(Long id, UserDTO user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with this id"));

        existingUser.setName(user.getName());
        existingUser.setProfilePicture(user.getProfilePicture());

        return userRepository.save(existingUser);
    }
}
