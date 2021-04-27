package com.example.operationsunlight.modules.login;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String email;
    private List<String> noteRefs;
    private String garden;

    public User() {    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email, List<String> noteRefs, String garden) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.noteRefs = noteRefs;
        this.garden = garden;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", noteRefs=" + noteRefs +
                ", garden='" + garden + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.username.equals(user.username)
                && PasswordEncoder.password_verify(this.password, user.password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getNoteRefs() {
        return noteRefs;
    }

    public void setNoteRefs(List<String> noteRefs) {
        this.noteRefs = noteRefs;
    }

    public String getGarden() {
        return garden;
    }

    public void setGarden(String garden) {
        this.garden = garden;
    }
}
