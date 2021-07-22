package com.example.usermanagement.payload.request;

import com.example.usermanagement.model.Role;

import java.util.Date;

public class SignupRequest {
    private String username;
    private String password;
    private String fullName;
    private Date dateOfBirth;
    private String address;
    private String description;
    private Role role;

    public SignupRequest() {
    }

    public SignupRequest(String username, String password, String fullName, Date dateOfBirth, String address, String description, Role role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.description = description;
        this.role = role;
    }

    public SignupRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
