package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service // or @Component
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        // return a list of students from the database
        return studentRepository.findAll();
    }

    public Student getStudent(Long studentId) {
        // find the student by id
        Optional<Student> student = studentRepository.findById(studentId);

        // throw an exception if the student is not found
        if (student.isEmpty()) {
            throw new IllegalStateException("Student with id " + studentId + " is not found.");
        }

        // return the student
        return student.get();
    }

    public void addStudent(Student student) {
        // check if the student with email already in the database
        Optional<Student> studentByEmail = studentRepository
                .findByEmail(student.getEmail());

        // throw an exception if the student with email already in the database
        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("Email " + student.getEmail() + " is already taken.");
        }

        // save the student to the database
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        // find the student by id
        boolean exists = studentRepository.existsById(studentId);

        // throw an exception if the student is not found
        if (!exists) {
            throw new IllegalStateException("Student with id " + studentId + " is not found.");
        }

        // delete the student from the database
        studentRepository.deleteById(studentId);
    }

    @Transactional // to avoid use of query in studentRepository
    public Student updateStudent(Long studentId, String name, String email) {
        // find the student by id
        Student student = studentRepository.findById(studentId).
                orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " is not found."));

        // update student's name record
        if (name != null &&
                name.length() > 0 &&
                !name.equals(student.getName())) {
            student.setName(name);
        }

        // update student's email record
        if (email != null &&
                email.length() > 0 &&
                !email.equals(student.getEmail())) {
            // check if the student with email already in the database
            Optional<Student> studentByEmail = studentRepository.findByEmail(email);
            if (studentByEmail.isPresent()) {
                throw new IllegalStateException("Email " + email + " is already taken.");
            }
            student.setEmail(email);
        }

        // save the student to the database
        studentRepository.save(student);

        // return the student
        return student;
    }
}
