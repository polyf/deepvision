package com.hub.deepvision.service;

import com.hub.deepvision.model.Label;
import com.hub.deepvision.model.dto.LabelDTO;
import com.hub.deepvision.repository.LabelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    @Autowired
    private final LabelRepository labelRepository;

    @Autowired
    private ModelMapper modelMapper;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label createLabel(LabelDTO label) {
        if (labelRepository.existsByName(label.getName())) {
            throw new DuplicateKeyException("Label with this name already exists");
        }
        return labelRepository.save(modelMapper.map(label, Label.class));
    }

    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    public Label getLabelById(Long id) {
        return labelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Label not found with this id"));
    }

    public void deleteLabelById(Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Label not found with this id"));
        labelRepository.delete(label);
    }

    public Label updateLabelById(Long id, LabelDTO labelDTO) {
        Label existingLabel = labelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Label not found with this id"));

        existingLabel.setName(labelDTO.getName());

        return labelRepository.save(existingLabel);
    }
}
