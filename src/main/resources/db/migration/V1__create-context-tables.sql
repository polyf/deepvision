CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    profile_picture VARCHAR,
    password VARCHAR NOT NULL
);

CREATE TABLE images (
    id SERIAL PRIMARY KEY,
    size BIGINT NOT NULL,
    content_type VARCHAR NOT NULL,
    file_name VARCHAR NOT NULL,
    file_url VARCHAR NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_user_images FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE labels (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_user_labels FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
