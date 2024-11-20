package com.hub.deepvision.service;

import com.amazonaws.services.s3.AmazonS3;
import com.hub.deepvision.model.User;
import com.hub.deepvision.model.dto.AddImageDTO;
import com.hub.deepvision.model.dto.AddUserDTO;
import com.hub.deepvision.model.dto.UpdateUserDTO;
import com.hub.deepvision.model.dto.UserDTO;
import com.hub.deepvision.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final BucketService bucketService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, BucketService bucketService) {
        this.userRepository = userRepository;
        this.bucketService = bucketService;
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


    public ResponseEntity<String> updateUserById(Long id, UpdateUserDTO user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with this id"));
        existingUser.setName(user.getName());
        userRepository.save(existingUser);
        return ResponseEntity.ok("User updated successfully");
    }

    public ResponseEntity<String> updateProfilePicture(Long id, MultipartFile file) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with this id"));
        String profilePictureUrl = bucketService.uploadImage(AddImageDTO.builder()
                        .file(file)
                        .fileName(file.getOriginalFilename())
                .build());
        existingUser.setProfilePicture(profilePictureUrl);
        userRepository.save(existingUser);
        return ResponseEntity.ok("User updated successfully");
    }
}
