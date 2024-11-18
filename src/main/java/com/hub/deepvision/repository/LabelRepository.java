package com.hub.deepvision.repository;

import com.hub.deepvision.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    Optional<Label> findById(Long id);

    boolean existsByName(String name);
}
