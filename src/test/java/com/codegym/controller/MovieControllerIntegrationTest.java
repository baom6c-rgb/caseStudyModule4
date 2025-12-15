package com.codegym.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.codegym.moviebackendjwt.controller.MovieController;
import com.codegym.moviebackendjwt.model.Movie;
import com.codegym.moviebackendjwt.service.impl.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MovieControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void whenNoMovies_thenReturnNoContent() throws Exception {
        when(movieService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenMoviesExist_thenReturnOkAndJsonArray() throws Exception {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Inception");
        movie.setDuration(148);

        when(movieService.findAll()).thenReturn(List.of(movie));

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Inception"))
                .andExpect(jsonPath("$[0].duration").value(148));
    }

    @Test
    void whenValidMoviePosted_thenReturnCreatedAndSavedMovie() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        Movie movieToSave = new Movie();
        movieToSave.setName("Interstellar");
        movieToSave.setDuration(169);

        Movie savedMovie = new Movie();
        savedMovie.setId(1L);
        savedMovie.setName("Interstellar");
        savedMovie.setDuration(169);

        when(movieService.save(any(Movie.class))).thenReturn(savedMovie);

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieToSave)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Interstellar"))
                .andExpect(jsonPath("$.duration").value(169));
    }
}
