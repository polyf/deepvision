package com.hub.deepvision.controller;

import com.hub.deepvision.model.dto.AddUserDTO;
import com.hub.deepvision.model.dto.LabelDTO;
import com.hub.deepvision.model.dto.UpdateUserDTO;
import com.hub.deepvision.model.dto.UserDTO;
import com.hub.deepvision.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                modelMapper.map(userService.getUserById(id), UserDTO.class)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editUser(@PathVariable Long id, @RequestBody UpdateUserDTO user) {
        return userService.updateUserById(id, user);
    }

    @PutMapping("/{id}/profile-picture")
    public ResponseEntity<String> editProfilePictureImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return userService.updateProfilePicture(id, file);
    }
}
