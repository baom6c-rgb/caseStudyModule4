package com.codegym.moviebackendjwt.controller;

import com.codegym.moviebackendjwt.model.Country;
import com.codegym.moviebackendjwt.model.Genre;
import com.codegym.moviebackendjwt.model.Movie;
import com.codegym.moviebackendjwt.model.dto.MovieRequestDTO;
import com.codegym.moviebackendjwt.repo.CountryRepo;
import com.codegym.moviebackendjwt.repo.GenreRepo;
import com.codegym.moviebackendjwt.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private GenreRepo genreRepo;

    // ================= GET LIST + FILTER =================
    @GetMapping
    public ResponseEntity<List<Movie>> findAllMovie(
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) Long countryId,
            @RequestParam(required = false) String status
    ) {

        if (genreId != null && countryId != null) {
            return ResponseEntity.ok(
                    movieService.findByGenreAndCountry(genreId, countryId)
            );
        }

        if (genreId != null) {
            return ResponseEntity.ok(
                    movieService.findByGenreId(genreId)
            );
        }

        if (countryId != null) {
            return ResponseEntity.ok(
                    movieService.findByCountryId(countryId)
            );
        }

        if (status != null) {
            return ResponseEntity.ok(
                    movieService.findByStatus(status)
            );
        }

        List<Movie> movies = (List<Movie>) movieService.findAll();
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(movies);
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable Long id) {
        return movieService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody MovieRequestDTO dto) {

        Movie movie = new Movie();
        applyDtoToMovie(movie, dto, true);

        return new ResponseEntity<>(movieService.save(movie), HttpStatus.CREATED);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable Long id,
            @RequestBody MovieRequestDTO dto
    ) {

        Movie movie = movieService.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        applyDtoToMovie(movie, dto, false);

        return ResponseEntity.ok(movieService.save(movie));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {

        if (movieService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        movieService.remove(id);
        return ResponseEntity.noContent().build();
    }

    // ================= HELPER =================
    private void applyDtoToMovie(Movie movie, MovieRequestDTO dto, boolean isCreate) {

        if (dto.getTitle() != null) movie.setTitle(dto.getTitle());
        if (dto.getDescription() != null) movie.setDescription(dto.getDescription());
        if (dto.getDuration() != null) movie.setDuration(dto.getDuration());
        if (dto.getReleaseYear() != null) movie.setReleaseYear(dto.getReleaseYear());
        if (dto.getRating() != null) movie.setRating(dto.getRating());
        if (dto.getStatus() != null) movie.setStatus(dto.getStatus());

        if (dto.getCountryId() != null) {
            Country country = countryRepo.findById(dto.getCountryId())
                    .orElseThrow(() -> new RuntimeException("Country not found"));
            movie.setCountry(country);
        } else if (isCreate) {
            throw new RuntimeException("Country is required");
        }

        if (dto.getGenreIds() != null && !dto.getGenreIds().isEmpty()) {
            Set<Genre> genres = new HashSet<>();
            for (Long gid : dto.getGenreIds()) {
                Genre genre = genreRepo.findById(gid)
                        .orElseThrow(() -> new RuntimeException("Genre not found: " + gid));
                genres.add(genre);
            }
            movie.setGenres(genres);
        } else if (isCreate) {
            throw new RuntimeException("Genres are required");
        }
    }
}
