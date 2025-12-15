package com.codegym.moviebackendjwt.controller;

import com.codegym.moviebackendjwt.model.Movie;
import com.codegym.moviebackendjwt.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @GetMapping
    public ResponseEntity<Iterable<Movie>> findAllMovie() {
        List<Movie> movies = (List<Movie>) movieService.findAll();
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.findById(id);
        if (!movieOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(movieOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(movieService.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable Long id,
            @RequestBody Movie movie) {

        Optional<Movie> movieOptional = movieService.findById(id);
        if (!movieOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        movie.setId(movieOptional.get().getId());
        return new ResponseEntity<>(movieService.save(movie), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.findById(id);
        if (!movieOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        movieService.remove(id);
        return new ResponseEntity<>(movieOptional.get(), HttpStatus.OK);
    }
}
