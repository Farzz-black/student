package com.example.studentservice.controller;

import com.example.studentservice.entity.Student;
import com.example.studentservice.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Validated
public class StudentController {

    @Autowired
    private StudentService studentService;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);


    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        logger.info("Creating student: {}", student);
        Student createdStudent = studentService.saveStudent(student);
        logger.info("Student created successfully: {}", createdStudent);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        logger.info("Fetching all students");
        List<Student> students = studentService.getAllStudents();
        logger.info("Students fetched successfully: {}", students);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

}
