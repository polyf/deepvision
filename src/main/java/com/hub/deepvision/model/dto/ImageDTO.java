package com.hub.deepvision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {

    private Long id;
    private String fileName;
    private String fileUrl;
    private String contentType;
    private Long size;
}