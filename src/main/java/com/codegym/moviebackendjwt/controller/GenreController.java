package com.codegym.moviebackendjwt.controller;

import com.codegym.moviebackendjwt.model.Genre;
import com.codegym.moviebackendjwt.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = "http://localhost:5173")
public class GenreController {

    @Autowired
    private IGenreService genreService;

    @GetMapping
    public ResponseEntity<Iterable<Genre>> findAll() {
        return new ResponseEntity<>(genreService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Genre> save(@RequestBody Genre genre) {
        return new ResponseEntity<>(genreService.save(genre), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> update(
            @PathVariable Long id,
            @RequestBody Genre genre
    ) {
        Optional<Genre> genreOptional = genreService.findById(id);
        if (genreOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        genre.setId(id);
        return new ResponseEntity<>(genreService.save(genre), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Genre> delete(@PathVariable Long id) {
        Optional<Genre> genreOptional = genreService.findById(id);
        if (genreOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        genreService.remove(id);
        return new ResponseEntity<>(genreOptional.get(), HttpStatus.OK);
    }
}
