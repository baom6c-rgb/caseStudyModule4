package com.codegym.moviebackendjwt.repo;

import com.codegym.moviebackendjwt.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepo extends JpaRepository<Genre, Long> {
}
