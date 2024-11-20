CREATE TABLE label_image (
    label_id BIGINT NOT NULL,
    image_id BIGINT NOT NULL,
    PRIMARY KEY (label_id, image_id),
    CONSTRAINT fk_label FOREIGN KEY (label_id) REFERENCES labels (id) ON DELETE CASCADE,
    CONSTRAINT fk_image FOREIGN KEY (image_id) REFERENCES images (id) ON DELETE CASCADE
);
