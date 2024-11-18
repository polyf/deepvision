package com.hub.deepvision.controller;

import com.hub.deepvision.model.dto.LabelDTO;
import com.hub.deepvision.service.LabelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<LabelDTO> addLabel(@RequestBody LabelDTO label) {
        return ResponseEntity.ok(
                modelMapper.map(labelService.createLabel(label), LabelDTO.class)
        );
    }

    @GetMapping
    public ResponseEntity<List<LabelDTO>> getAllLabels() {
        return ResponseEntity.ok(
                labelService.getAllLabels().stream()
                        .map(label -> modelMapper.map(label, LabelDTO.class))
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelDTO> getLabelById(@PathVariable Long id) {
        return ResponseEntity.ok(
                modelMapper.map(labelService.getLabelById(id), LabelDTO.class)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabelById(@PathVariable Long id) {
        labelService.deleteLabelById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabelDTO> editLabel(@PathVariable Long id, @RequestBody LabelDTO label) {
        return ResponseEntity.ok(
                modelMapper.map(labelService.updateLabelById(id, label), LabelDTO.class)
        );
    }

}
