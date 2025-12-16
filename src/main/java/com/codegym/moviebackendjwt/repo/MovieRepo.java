package com.codegym.moviebackendjwt.repo;

import com.codegym.moviebackendjwt.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long> {

    @Query("""
        SELECT DISTINCT m
        FROM Movie m
        JOIN m.genres g
        WHERE g.id = :genreId
    """)
    List<Movie> findByGenreId(Long genreId);

    @Query("""
        SELECT m
        FROM Movie m
        WHERE m.country.id = :countryId
    """)
    List<Movie> findByCountryId(Long countryId);

    @Query("""
        SELECT DISTINCT m
        FROM Movie m
        JOIN m.genres g
        WHERE g.id = :genreId
        AND m.country.id = :countryId
    """)
    List<Movie> findByGenreAndCountry(Long genreId, Long countryId);

    List<Movie> findByStatus(String status);
}
