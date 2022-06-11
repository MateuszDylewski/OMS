package com.oms.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oms.enums.UserRole;

@Entity
@Table(name = "User")
public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String occupation;
    private User manager;
    private LocalDate dateOfBirth;
    private UserRole userRole;


    protected User() {}
    public User(String firstName, String lastName,
                String email, String occupation, User manager, LocalDate dateOfBirth,
                UserRole userRole) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setOccupation(occupation);
        setManager(manager);
        setDateOfBirth(dateOfBirth);
        setUserRole(userRole);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Basic
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        if(firstName == null || firstName.length() <= 0) {
            throw new IllegalArgumentException("models.User: First name is null or empty");
        }
        this.firstName = firstName;
    }


    @Basic
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        if(lastName == null || lastName.length() <= 0) {
            throw new IllegalArgumentException("models.User: Last name is null or empty");
        }
        this.lastName = lastName;
    }


    @Basic
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if(email == null || email.length() <= 0){
            throw new IllegalArgumentException("models.User: Email is null or empty.");
        }

        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        if(!matcher.find()) {
            throw new IllegalArgumentException("models.User: Email incorrect.");
        }

        this.email = email;
    }

    @OneToOne
    public User getManager() {
        return manager;
    }
    public void setManager(User manager) {
        this.manager = manager;
    }


    @Basic
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        if(occupation == null || occupation.length() <= 0){
            throw new IllegalArgumentException("models.User: Occupation is null or empty.");
        }
        this.occupation = occupation;
    }


    @Basic
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        if(dateOfBirth == null){
            throw new IllegalArgumentException("models.User: Date of birth is null.");
        }
        if(dateOfBirth.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("models.User: Date of birth is set to the future.");
        }
        this.dateOfBirth = dateOfBirth;
    }


    @Enumerated(EnumType.STRING)
    public UserRole getUserRole() {
        return userRole;
    }
    public void setUserRole(UserRole userRole) {
        if(userRole == null){
            throw new IllegalArgumentException("models.User: User role is null");
        }
        this.userRole = userRole;
    }
}
