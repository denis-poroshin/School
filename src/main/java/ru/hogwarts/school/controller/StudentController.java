package ru.hogwarts.school.controller;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping
    public Student addStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody Student student){
        studentService.updateStudent(id,student);
    }
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id){
        return studentService.getStudent(id);
    }
    @DeleteMapping("/{id}")
    public Student removeStudent(@PathVariable Long id){
        return studentService.removeStudent(id);
    }
    @GetMapping
    public Collection<Student> getAllStudent(){
        return studentService.getAllStudent();
    }
//    @GetMapping("age/{age}")
//    public Collection<Student> searchForStudentsByColor(@PathVariable int age){
//        return studentService.searchForStudentsByAge(age);
//    }
}
