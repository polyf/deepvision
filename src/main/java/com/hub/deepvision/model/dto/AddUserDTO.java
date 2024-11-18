package com.hub.deepvision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserDTO {
    private String name;
    private String profilePicture;
    private String email;
    private String password;
}
