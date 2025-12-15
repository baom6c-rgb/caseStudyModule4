package com.codegym.moviebackendjwt.repo;

import com.codegym.moviebackendjwt.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Roles, Long> {

    Roles findByName(String name);
}
