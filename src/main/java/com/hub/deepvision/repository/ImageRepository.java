package com.hub.deepvision.repository;

import com.hub.deepvision.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i JOIN i.labels l WHERE l.id = :labelId")
    List<Image> findByLabelId(Long labelId);

    @Query("SELECT i FROM Image i JOIN i.labels l " +
            "WHERE LOWER(i.fileName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(l.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Image> findByKeyword(String keyword);

}
