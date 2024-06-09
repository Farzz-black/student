package com.example.studentservice.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TransactionModel {

        private Long Id;
        private double amount;
        private String term;


    }

