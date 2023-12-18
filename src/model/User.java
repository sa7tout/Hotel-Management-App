package model;

import java.sql.Timestamp;

public class User {
    private final int userID;
    private String username;
    private String fullName;
    private String email;
    private UserRole role;
    private Timestamp createdAt;

    public User(int userID, String username, String fullName, String email, UserRole role, Timestamp createdAt) {
        this.userID = userID;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean login(String email, String password) {
        // implement login functionality
        return true;
    }

    public void changePassword(String oldPassword, String newPassword) {
        // implement change password functionality
    }

    public void updateProfileInformation(String firstName, String lastName, String email) {
        // implement update profile information functionality
    }

    public void accessUserSpecificFeatures() {
        // implement access user-specific features based on type functionality
    }
    public enum UserRole {
        ADMIN,AGENT
    }
}


