package com.hub.deepvision.service;

import com.amazonaws.services.s3.AmazonS3;
import com.hub.deepvision.model.Image;
import com.hub.deepvision.model.Label;
import com.hub.deepvision.model.dto.AddImageDTO;
import com.hub.deepvision.model.dto.EditImageDTO;
import com.hub.deepvision.repository.ImageRepository;
import com.hub.deepvision.repository.LabelRepository;
import com.hub.deepvision.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 S3Client;

    @Autowired
    private final ImageRepository imageRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final LabelRepository labelRepository;

    private static final Long userId = 7L;

    public ImageService(ImageRepository imageRepository, UserRepository userRepository, LabelRepository labelRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.labelRepository = labelRepository;
    }

    public Image upload(AddImageDTO addImageDTO) {
        String imageUrl = uploadImage(addImageDTO);
        Image image = setObjectImageData(addImageDTO, imageUrl);
        List<Label> relatedLabels = addImageDTO.getLabels().stream()
                .map(labelId -> labelRepository.findById(labelId)
                        .orElseThrow(() -> new IllegalArgumentException("Label not found: " + labelId)))
                .toList();

        image.setLabels(relatedLabels);
        relatedLabels.forEach(label -> label.getImages().add(image));
        return imageRepository.save(image);
    }

    private Image setObjectImageData(AddImageDTO addImageDTO, String imageUrl) {
        return Image.builder()
                .size(addImageDTO.getFile().getSize())
                .contentType(addImageDTO.getFile().getContentType())
                .fileName(addImageDTO.getFileName())
                .fileUrl(imageUrl)
                .user(userRepository.getReferenceById(userId))
                .build();
    }

    private String uploadImage(AddImageDTO addImageDTO) {
        String fileName = UUID.randomUUID() + "-" + addImageDTO.getFileName();
        try {
            File file = this.convertMultipartToFile(addImageDTO);
            S3Client.putObject(bucketName, fileName, file);
            file.delete();
            return S3Client.getUrl(bucketName, fileName).toString();
        } catch (Exception ex) {
            System.out.println("Erro ao subir arquivo");
            return null;
        }
    }

    private File convertMultipartToFile(AddImageDTO addImageDTO) throws IOException {
        File file = new File(Objects.requireNonNull(addImageDTO.getFileName()));
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(addImageDTO.getFile().getBytes());
        fileOutputStream.close();
        return file;
    }

    public List<Image> getImagesByLabel(Long labelId) {
        return imageRepository.findByLabelId(labelId);
    }

    public List<Image> getImagesBySearch(String keyword) {
        return imageRepository.findByKeyword(keyword);
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with this id"));
    }

    public Image editImage(Long id, EditImageDTO editImageDTO) {
        Image existingImage = imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with this id"));

        existingImage.setFileName(editImageDTO.getName());

        listLabelsToRemove(editImageDTO.getLabels(), existingImage.getLabels()).forEach(label -> {
            existingImage.getLabels().remove(label);
            label.getImages().remove(existingImage);
        });

        listLabelsToAdd(editImageDTO.getLabels(), existingImage.getLabels()).forEach(label -> {
            existingImage.getLabels().add(label);
            label.getImages().add(existingImage);
        });

        return imageRepository.save(existingImage);
    }

    private List<Label> listLabelsToRemove(List<Long> newLabelIds, List<Label> existingLabels) {
        return existingLabels.stream()
                .filter(label -> !newLabelIds.contains(label.getId()))
                .toList();
    }

    private List<Label> listLabelsToAdd(List<Long> newLabelIds, List<Label> existingLabels) {
        return labelRepository.findAllById(newLabelIds).stream()
                .filter(label -> !existingLabels.contains(label))
                .toList();
    }

}
