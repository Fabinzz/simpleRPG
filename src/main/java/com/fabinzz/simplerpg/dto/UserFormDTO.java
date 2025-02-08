package com.fabinzz.simplerpg.dto;

import com.fabinzz.simplerpg.model.User;


public class UserFormDTO {
    private String id;
    private String name;
    private String email;

    public UserFormDTO() {
    }

    public UserFormDTO(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User converter(){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        return user;
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
