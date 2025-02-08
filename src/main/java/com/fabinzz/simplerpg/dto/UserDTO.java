package com.fabinzz.simplerpg.dto;

import com.fabinzz.simplerpg.model.User;

import java.util.List;
import java.util.stream.Collectors;


public class UserDTO {
    private String id;
    private String name;
    private String email;


    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public UserDTO() {
    }


    public static List<UserDTO> converter(List<User> users){
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
