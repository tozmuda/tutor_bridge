package org.tutorBridge.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tutorBridge.dto.StudentRegisterDTO;
import org.tutorBridge.dto.StudentUpdateDTO;
import org.tutorBridge.entities.Student;
import org.tutorBridge.repositories.StudentRepo;
import org.tutorBridge.repositories.UserRepo;
import org.tutorBridge.validation.ValidationException;


@Service
public class StudentService extends UserService<Student> {
    private final StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo, UserRepo userDao, PasswordEncoder passwordEncoder) {
        super(userDao, passwordEncoder);
        this.studentRepo = studentRepo;
    }

    private static StudentUpdateDTO fromStudentToDTO(Student student) {
        return new StudentUpdateDTO(
                student.getFirstName(),
                student.getLastName(),
                student.getPhone(),
                student.getBirthDate(),
                student.getLevel()
        );
    }

    @Transactional
    public void registerStudent(StudentRegisterDTO studentData) {
        Student student = new Student(
                studentData.getFirstName(),
                studentData.getLastName(),
                studentData.getPhone(),
                studentData.getEmail(),
                studentData.getPassword(),
                studentData.getLevel(),
                studentData.getBirthDate()
        );
        registerUser(student);
    }

    @Override
    protected void saveUser(Student user) {
        studentRepo.save(user);
    }

    @Transactional
    public StudentUpdateDTO updateStudentInfo(Student student, StudentUpdateDTO studentData) {
        if (studentData.getFirstName() != null) student.setFirstName(studentData.getFirstName());
        if (studentData.getLastName() != null) student.setLastName(studentData.getLastName());
        if (studentData.getPhone() != null) student.setPhone(studentData.getPhone());
        if (studentData.getBirthDate() != null) student.setBirthDate(studentData.getBirthDate());
        if (studentData.getLevel() != null) student.setLevel(studentData.getLevel());

        studentRepo.update(student);

        return fromStudentToDTO(student);
    }

    @Transactional(readOnly = true)
    public StudentUpdateDTO getStudentInfo(Student student) {
        return fromStudentToDTO(student);
    }


    public Student fromEmail(String email) {
        return studentRepo
                .findByEmail(email)
                .orElseThrow(() -> new ValidationException("Student not found"));
    }
}
