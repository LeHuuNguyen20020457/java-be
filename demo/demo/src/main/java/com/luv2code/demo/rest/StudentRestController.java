package com.luv2code.demo.rest;

import com.luv2code.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;

    @PostConstruct
    public void loadData() {
        this.theStudents = new ArrayList<>();

        theStudents.add(new Student("HAHA", "NANA"));
        theStudents.add(new Student("HAHA", "NANA"));
        theStudents.add(new Student("MÂMMA", "NÂN"));
        theStudents.add(new Student("HAHA", "NANA"));
    }



    // define endpoint for "/students" return a list student

    @GetMapping("/students")
    public List<Student> getStudents() {
        return  theStudents;
    }


    //defind endpoint or "/students/{studentId}" - return student at index

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {
        if((studentId >= theStudents.size()) || (studentId < 0)) {
            throw new StudentNotFoundException("Student is not found - " + studentId);
        }

        return  theStudents.get(studentId);
    }



    // Add an exception handler using @ExceptionHandler

}
