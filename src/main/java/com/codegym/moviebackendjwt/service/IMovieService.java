package com.codegym.moviebackendjwt.service;

import com.codegym.moviebackendjwt.model.Movie;

import java.util.List;

public interface IMovieService extends IGeneralService<Movie> {

    List<Movie> findByGenreId(Long genreId);

    List<Movie> findByCountryId(Long countryId);

    List<Movie> findByGenreAndCountry(Long genreId, Long countryId);

    List<Movie> findByStatus(String status);
}
