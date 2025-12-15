package com.codegym.moviebackendjwt.model.dto;

import com.codegym.moviebackendjwt.model.Roles;
import java.util.Set;

public class UserDTO {

    private Long id;
    private String name;
    private Set<Roles> roles;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, Set<Roles> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
