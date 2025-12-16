package com.codegym.moviebackendjwt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int duration;

    @Column(name = "release_year")
    private Integer releaseYear;

    private Double rating;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Country country;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonIgnoreProperties({"movies", "hibernateLazyInitializer", "handler"})
    private Set<Genre> genres;

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Double getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public Country getCountry() {
        return country;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
}
