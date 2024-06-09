package com.example.studentservice.service;

import com.example.studentservice.entity.Student;
import com.example.studentservice.exception.StudentServiceException;
import com.example.studentservice.model.TransactionModel;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    public Student saveStudent(Student student) {
        try {
            Student savedStudent = studentRepository.save(student);
            saveTransaction(savedStudent);
            return savedStudent;
        } catch (Exception ex) {
            throw new StudentServiceException("An error occurred while saving the student");
        }
    }

    private void saveTransaction(Student savedStudent) {
        String transactionServiceUrl = discoveryClient.getInstances("TRANSACTION-SERVICE")
                .stream()
                .findFirst()
                .map(instance -> instance.getUri().toString())
                .orElseThrow(() -> new IllegalStateException("Transaction service not found"));
        restTemplate.postForObject(transactionServiceUrl + "/transactions", savedStudent, Void.class);
    }

    public List<Student> getAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (Exception ex) {
            throw new StudentServiceException("An error occurred while fetching students");
        }
    }
}
