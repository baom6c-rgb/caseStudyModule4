package com.codegym.moviebackendjwt.service;

import com.codegym.moviebackendjwt.model.Roles;
import com.codegym.moviebackendjwt.model.Users;
import com.codegym.moviebackendjwt.repo.RoleRepo;
import com.codegym.moviebackendjwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Roles roleUser = roleRepo.findByName("ROLE_USER");
        user.setRoles(Set.of(roleUser));
        return repo.save(user);
    }

    public String verify(Users user) {

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword()
                        )
                );

        if (authentication.isAuthenticated()) {

            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();

            return jwtService.generateToken(
                    userDetails.getUsername()
            );
        }

        return "fail";
    }
}
