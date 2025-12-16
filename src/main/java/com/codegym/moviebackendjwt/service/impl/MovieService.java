package com.codegym.moviebackendjwt.service.impl;

import com.codegym.moviebackendjwt.model.Movie;
import com.codegym.moviebackendjwt.repo.MovieRepo;
import com.codegym.moviebackendjwt.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements IMovieService {

    @Autowired
    private MovieRepo movieRepo;

    @Override
    public Iterable<Movie> findAll() {
        return movieRepo.findAll();
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieRepo.findById(id);
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepo.save(movie);
    }

    @Override
    public void remove(Long id) {
        movieRepo.deleteById(id);
    }

    @Override
    public List<Movie> findByGenreId(Long genreId) {
        return movieRepo.findByGenreId(genreId);
    }

    @Override
    public List<Movie> findByCountryId(Long countryId) {
        return movieRepo.findByCountryId(countryId);
    }

    @Override
    public List<Movie> findByGenreAndCountry(Long genreId, Long countryId) {
        return movieRepo.findByGenreAndCountry(genreId, countryId);
    }

    @Override
    public List<Movie> findByStatus(String status) {
        return movieRepo.findByStatus(status);
    }
}
