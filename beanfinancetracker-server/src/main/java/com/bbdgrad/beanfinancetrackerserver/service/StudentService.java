package com.bbdgrad.beanfinancetrackerserver.service;


import com.bbdgrad.beanfinancetrackerserver.model.Student;
import com.bbdgrad.beanfinancetrackerserver.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Transactional
    public void updateStudent(Long id, String name, LocalDate dob, String email) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            Student updatedStudent = studentOptional.get();
            if (name != null && !name.isBlank() && !name.equals(updatedStudent.getName())) {
                updatedStudent.setName(name);
            }
            if (dob != null && !dob.equals(updatedStudent.getDob())) {
                updatedStudent.setDob(dob);
            }
            if (email != null && !email.isBlank() && !email.equals(updatedStudent.getEmail())) {
                Optional<Student> emailStudent = studentRepository.findByEmail(email);
                if (emailStudent.isPresent()) {
                    throw new IllegalStateException("Email already registered");
                }
                updatedStudent.setEmail(email);
            }
        } else {
            throw new IllegalStateException("Student with id " + id + " not found");
        }
    }
}
