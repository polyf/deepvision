package com.hub.deepvision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDetailsDTO {
    private Long id;
    private String fileName;
    private String fileUrl;
    private String contentType;
    private List<LabelDetailsDTO> labels;
}
