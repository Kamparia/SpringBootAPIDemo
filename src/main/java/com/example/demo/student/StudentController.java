package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.response.ResponseHandler;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Object> getStudents() {
        // get students
        List<Student> students = studentService.getStudents();
        return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, students);
    }

    @GetMapping
    @RequestMapping(path="/{studentId}")
    public ResponseEntity<Object> getStudent(@PathVariable Long studentId) {
        // get student with the given studentId
        Student student = studentService.getStudent(studentId);
        return ResponseHandler.generateResponse("Successfully retrieved student data!", HttpStatus.OK, student);
    }

    @PostMapping
    public ResponseEntity<Object> registerNewStudent(@RequestBody Student student) {
        // takes the API request and converts it to a Student object
        studentService.addStudent(student);
        return ResponseHandler.generateResponse("Successfully added new student!", HttpStatus.OK, student);
    }

    @DeleteMapping(path="/{studentId}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long studentId) {
        // delete student with the given studentId
        studentService.deleteStudent(studentId);
        return ResponseHandler.generateResponse("Successfully deleted student!", HttpStatus.OK, null);
    }

    @PutMapping(path="/{studentId}")
    public ResponseEntity<Object> updateStudent(
            @PathVariable Long studentId,
            @RequestParam(value="name", required=false) String name,
            @RequestParam(value="email", required=false) String email) {
        // update student with the given studentId
        Student student = studentService.updateStudent(studentId, name, email);
        return ResponseHandler.generateResponse("Successfully updated student!", HttpStatus.OK, student);
    }
}