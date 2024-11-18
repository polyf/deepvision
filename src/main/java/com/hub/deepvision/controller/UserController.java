package com.hub.deepvision.controller;

import com.hub.deepvision.model.dto.AddUserDTO;
import com.hub.deepvision.model.dto.LabelDTO;
import com.hub.deepvision.model.dto.UserDTO;
import com.hub.deepvision.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody AddUserDTO user) {
        return ResponseEntity.ok(
                modelMapper.map(userService.createUser(user), UserDTO.class)
        );
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers().stream()
                        .map(user -> modelMapper.map(user, UserDTO.class))
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                modelMapper.map(userService.getUserById(id), UserDTO.class)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> editUser(@PathVariable Long id, @RequestBody UserDTO user) {
        return ResponseEntity.ok(
                modelMapper.map(userService.updateUserById(id, user), UserDTO.class)
        );
    }
}
