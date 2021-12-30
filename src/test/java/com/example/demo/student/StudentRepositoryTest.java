package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        // Clean up after each test
        underTest.deleteAll();
    }

    @Test
    void checkIfEmailDoesExist() {
        // Test to check if the email is present in the database

        // Given
        String email = "jd@example.com";
        Student student = new Student(
                "John Doe",
                email,
                LocalDate.of(1990, 1, 1)
        );
        underTest.save(student);

        Optional<Student> found = underTest.findByEmail(email);  // When
        assertThat(found.isPresent()).isTrue();  // Then
    }

    @Test
    void checkIfEmailDoesNotExist() {
        // Test to check if the email does not exist in the database

        String email = "jd@example.com";  // Given
        Optional<Student> found = underTest.findByEmail(email);  // When
        assertThat(found.isPresent()).isFalse();  // Then
    }
}
