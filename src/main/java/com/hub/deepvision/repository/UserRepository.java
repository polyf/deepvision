package com.hub.deepvision.repository;

import com.hub.deepvision.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);
}
