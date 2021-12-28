package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner CommandLineRunner(StudentRepository repository) {
        return args -> {
            // create student object called john
            Student john = new Student(
                    "John Doe",
                    "jd@example.com",
                    LocalDate.of(1990, 1, 1)
            );

            // create student object called alex
            Student alex = new Student(
                    "Alex Smith",
                    "asmith@example.com",
                    LocalDate.of(1995, 12, 21)
            );

            // save both students to the database
            repository.saveAll(
                    List.of(john, alex)
            );
        };
    }
}
