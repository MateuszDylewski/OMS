package com.oms.DAO;

import com.oms.enums.UserRole;

import java.time.LocalDate;

public class IncomingUserDAO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String occupation;
    private LocalDate dateOfBirth;
    private UserRole userRole;
    private Long managerId;

    public IncomingUserDAO(Long userId, String firstName, String lastName,
                           String email, String occupation, LocalDate dateOfBirth,
                           UserRole userRole, Long managerId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.occupation = occupation;
        this.dateOfBirth = dateOfBirth;
        this.userRole = userRole;
        this.managerId = managerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "IncomingUserDAO{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", occupation='" + occupation + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", role=" + userRole +
                ", managerId=" + managerId +
                '}';
    }
}
