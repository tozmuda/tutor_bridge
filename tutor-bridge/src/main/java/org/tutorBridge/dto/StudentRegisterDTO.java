package org.tutorBridge.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.tutorBridge.entities.Student;
import org.tutorBridge.entities.enums.StudentLevel;
import org.tutorBridge.validation.PhoneNumber;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link Student}
 */
public class StudentRegisterDTO implements Serializable {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Phone number is required")
    @PhoneNumber
    private String phone;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotNull(message = "Birthdate is required")
    @Past(message = "Birthdate must be in the past")
    private LocalDate birthDate;
    @NotNull(message = "Student level is required")
    private StudentLevel level;

    public StudentRegisterDTO(String firstName, String lastName, String phone, String email, String password, LocalDate birthDate, StudentLevel level) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.level = level;
    }

    public StudentRegisterDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public StudentLevel getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRegisterDTO entity = (StudentRegisterDTO) o;
        return Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.password, entity.password) &&
                Objects.equals(this.birthDate, entity.birthDate) &&
                Objects.equals(this.level, entity.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phone, email, password, birthDate, level);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "phone = " + phone + ", " +
                "email = " + email + ", " +
                "password = " + password + ", " +
                "birthDate = " + birthDate + ", " +
                "level = " + level + ")";
    }
}