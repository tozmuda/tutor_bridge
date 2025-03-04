package org.tutorBridge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.tutorBridge.entities.enums.UserType;
import org.tutorBridge.validation.PhoneNumber;

import java.time.LocalDate;

@Entity
@Table(name = "\"User\"", uniqueConstraints = {
        @UniqueConstraint(columnNames = "EMAIL")
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID", nullable = false)
    private Long userId;

    @NotBlank(message = "First name is required")
    @Column(name = "FIRSTNAME", nullable = false, length = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "LASTNAME", nullable = false, length = 100)
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @PhoneNumber
    @Column(name = "PHONE", nullable = false, length = 20)
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;


    @NotBlank(message = "Password is required")
    @Column(name = "PASSWORD", nullable = false, length = 1024)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 100)
    private UserType type;

    @NotNull(message = "Birthdate is required")
    @Past(message = "Birthdate must be in the past")
    @Column(name = "BIRTHDATE", nullable = false)
    private LocalDate birthDate;

    public User(String firstName, String lastName, String phone, String email, String password, UserType type, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.type = type;
        this.birthDate = birthDate;
    }

    public User() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
