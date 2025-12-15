package com.codegym.moviebackendjwt.repo;

import com.codegym.moviebackendjwt.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long> {

}
