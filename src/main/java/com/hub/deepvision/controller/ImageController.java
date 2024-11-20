package com.hub.deepvision.controller;

import com.hub.deepvision.model.Image;
import com.hub.deepvision.model.dto.AddImageDTO;
import com.hub.deepvision.model.dto.EditImageDTO;
import com.hub.deepvision.model.dto.ImageDTO;
import com.hub.deepvision.model.dto.ImageDetailsDTO;
import com.hub.deepvision.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageDTO> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("labels") List<Long> labels,
            @RequestParam("fileName") String fileName)
    {
        ImageDTO image = modelMapper.map(imageService.upload(
                AddImageDTO.builder()
                        .file(file)
                        .fileName(fileName)
                        .labels(labels)
                .build()), ImageDTO.class);
        return ResponseEntity.ok(image);
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getImagesByLabel(@RequestParam("labelId") Long labelId) {
        return ResponseEntity.ok(
                imageService.getImagesByLabel(labelId).stream()
                        .map(image -> modelMapper.map(image, ImageDTO.class))
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDetailsDTO> getImageById(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(imageService.getImageById(id), ImageDetailsDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDetailsDTO> editImage(@PathVariable Long id, @RequestBody EditImageDTO editImageDTO) {
        return ResponseEntity.ok(modelMapper.map(imageService.editImage(id, editImageDTO), ImageDetailsDTO.class));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ImageDTO>> getImagesBySearch(@RequestParam String keyword) {
        return ResponseEntity.ok(
                imageService.getImagesBySearch(keyword).stream()
                        .map(image -> modelMapper.map(image, ImageDTO.class))
                        .toList()
        );
    }

}
