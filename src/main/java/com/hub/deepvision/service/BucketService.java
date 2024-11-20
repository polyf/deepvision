package com.hub.deepvision.service;

import com.amazonaws.services.s3.AmazonS3;
import com.hub.deepvision.model.Image;
import com.hub.deepvision.model.dto.AddImageDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@Getter
@Setter
public class BucketService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 S3Client;

    @Autowired
    public BucketService(AmazonS3 S3Client) {
        this.S3Client = S3Client;
    }

    public String uploadImage(AddImageDTO addImageDTO) {
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
}
