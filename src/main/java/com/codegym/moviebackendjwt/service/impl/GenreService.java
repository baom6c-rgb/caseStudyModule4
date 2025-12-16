package com.codegym.moviebackendjwt.service.impl;

import com.codegym.moviebackendjwt.model.Genre;
import com.codegym.moviebackendjwt.repo.GenreRepo;
import com.codegym.moviebackendjwt.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreService implements IGenreService {

    @Autowired
    private GenreRepo genreRepo;

    @Override
    public Iterable<Genre> findAll() {
        return genreRepo.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepo.findById(id);
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepo.save(genre);
    }

    @Override
    public void remove(Long id) {
        genreRepo.deleteById(id);
    }
}
