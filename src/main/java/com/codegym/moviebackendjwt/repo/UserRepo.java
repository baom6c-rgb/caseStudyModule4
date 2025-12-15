package com.codegym.moviebackendjwt.repo;

import com.codegym.moviebackendjwt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    Users findByUsername(String username);
}
