package com.hub.deepvision.model.dto;

import com.hub.deepvision.model.UserRole;

public record RegisterDTO (String email, String password, UserRole role,
                           String fullName) {

}
