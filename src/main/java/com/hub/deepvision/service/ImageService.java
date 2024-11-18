package com.hub.deepvision.service;

import com.hub.deepvision.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    public Image uploadImage(MultipartFile file) {
        return Image.builder().build();
    }
}
