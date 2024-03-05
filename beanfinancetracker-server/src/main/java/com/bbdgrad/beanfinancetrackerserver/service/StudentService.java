package com.bbdgrad.beanfinancetrackerserver.service;


import com.bbdgrad.beanfinancetrackerserver.model.Student;
import com.bbdgrad.beanfinancetrackerserver.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        Optional<List<Student>> studentList = Optional.of(studentRepository.findAll());
        return studentList.orElseGet(List::of);
    }

    public void registerStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email already registered");
        }
        System.out.println("Adding student " + student);
        studentRepository.save(student);
    }

    public void removeStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalStateException("Student with id " + id + " not found");
        }
        studentRepository.deleteById(id);
    }
}
