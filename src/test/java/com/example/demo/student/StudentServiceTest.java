package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    // Mock Tests

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void canGetStudents() {
        // Verify that the method findAll() was called in getStudents()

        underTest.getStudents();  // when
        verify(studentRepository).findAll();  // then
    }

    @Test
    @Disabled
    void cantGetStudent() {

    }

    @Test
    void canGetStudentThrowError() {
        Long studentId = 1L;

        given(studentRepository.findById(studentId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getStudent(studentId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id " + studentId + " is not found.");
    }

    @Test
    void canAddStudent() {
        // Check that StudentRepository was invoked with the correct parameters

        // Given
        Student student = new Student(
                "John Doe",
                "jd@example.com",
                LocalDate.of(1990, 1, 1)
        );

        // When
        underTest.addStudent(student);

        // Then
        // Compare given Student with the Student returned by the StudentRepository
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        Student savedStudent = studentArgumentCaptor.getValue();

        assertThat(savedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // Throw an exception when the email is already taken

        // Given
        String email = "jd@example.com";
        Student student = new Student(
                "John Doe",
                email,
                LocalDate.of(1990, 1, 1)
        );

        given(studentRepository.findByEmail(email)).willReturn(Optional.of(student));

        // When
        // Then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email " + email + " is already taken.");

        // Verify that this test never saved the student
        verify(studentRepository, never()).save(student);
    }

    @Test
    @Disabled
    void canDeleteStudent() {
        // save a student
        String email = "alan.turing@example.com";
        Student student = new Student(
                "Alan Turing",
                email,
                LocalDate.of(1990, 1, 1)
        );
        studentRepository.save(student);

        // delete the student
        underTest.deleteStudent(student.getId());
        verify(studentRepository).deleteById(student.getId());
    }

    @Test
    void canDeleteStudentThrowError() {
        Long studentId = 1L;

        given(studentRepository.existsById(studentId)).willReturn(false);

        assertThatThrownBy(() -> underTest.deleteStudent(studentId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id " + studentId + " is not found.");

        verify(studentRepository, never()).deleteById(studentId);
    }

    @Test
    void canUpdateStudent() {
    }
}