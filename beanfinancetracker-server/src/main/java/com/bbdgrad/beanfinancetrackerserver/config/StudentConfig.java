package com.bbdgrad.beanfinancetrackerserver.config;

import com.bbdgrad.beanfinancetrackerserver.model.Student;
import com.bbdgrad.beanfinancetrackerserver.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner (StudentRepository repository) {
        //Insert some test data into db.
        //With the current configuration this will get cleared along with the tables when app stops
        return args -> {
            Student john = new Student("John", LocalDate.of(2004, Month.JANUARY, 1), "john@example.com");
            Student fred = new Student("Fred", LocalDate.of(2004, Month.JANUARY, 1), "fred@example.com");

            repository.saveAll(List.of(john, fred));
        };
    }

}
