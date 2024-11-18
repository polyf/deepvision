package com.hub.deepvision.controller;

import com.hub.deepvision.model.dto.ImageDTO;
import com.hub.deepvision.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageDTO> uploadImage(@RequestPart("file") MultipartFile file) {
        ImageDTO savedImage = modelMapper.map(imageService.uploadImage(file), ImageDTO.class);
        return ResponseEntity.ok(savedImage);
    }
}
