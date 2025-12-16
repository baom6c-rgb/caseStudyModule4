package com.codegym.moviebackendjwt.controller;

import com.codegym.moviebackendjwt.model.Country;
import com.codegym.moviebackendjwt.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins = "http://localhost:5173")
public class CountryController {

    @Autowired
    private ICountryService countryService;

    @GetMapping
    public ResponseEntity<Iterable<Country>> findAll() {
        return new ResponseEntity<>(countryService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> save(@RequestBody Country country) {
        return new ResponseEntity<>(countryService.save(country), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> update(
            @PathVariable Long id,
            @RequestBody Country country
    ) {
        Optional<Country> countryOptional = countryService.findById(id);
        if (countryOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        country.setId(id);
        return new ResponseEntity<>(countryService.save(country), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Country> delete(@PathVariable Long id) {
        Optional<Country> countryOptional = countryService.findById(id);
        if (countryOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        countryService.remove(id);
        return new ResponseEntity<>(countryOptional.get(), HttpStatus.OK);
    }
}
