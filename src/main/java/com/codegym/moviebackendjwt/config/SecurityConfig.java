package com.codegym.moviebackendjwt.config;

import com.codegym.moviebackendjwt.service.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) ->
                                res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                        .accessDeniedHandler((req, res, e) ->
                                res.sendError(HttpServletResponse.SC_FORBIDDEN))
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/login", "/register", "/error")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/movies/**")
                        .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/movies/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/movies/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/movies/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/genres/**")
                        .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/genres/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/genres/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/genres/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/countries/**")
                        .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/countries/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/countries/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/countries/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/users/**")
                        .hasAuthority("ROLE_ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(myUserDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
