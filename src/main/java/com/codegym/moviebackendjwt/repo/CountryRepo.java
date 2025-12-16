package com.codegym.moviebackendjwt.repo;

import com.codegym.moviebackendjwt.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends JpaRepository<Country, Long> {
}
