package com.bbdgrad.beanfinancetrackerserver.controller;

import com.bbdgrad.beanfinancetrackerserver.model.Student;
import com.bbdgrad.beanfinancetrackerserver.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping(path = "")
    public void registerStudent(@RequestBody Student student) {
        studentService.registerStudent(student);
    }

    @DeleteMapping(path = "/{studentId}")
    public void removeStudent(@PathVariable("studentId") Long studentId) {
        studentService.removeStudent(studentId);
    }

    @PutMapping(path = "/{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) LocalDate dob,
                              @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, dob, email);
    }

}
