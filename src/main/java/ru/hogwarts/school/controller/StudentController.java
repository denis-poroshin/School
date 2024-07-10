package ru.hogwarts.school.controller;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Optional;

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
    @GetMapping("/")
    public Collection<Student> searchForStudentsByColor(@RequestParam("age") int age){
        return studentService.searchForStudentsByAge(age);
    }
    @GetMapping("/age/range")
    public Collection<Student> findStudentsByAgeBetween(@RequestParam("minAge") int minAge,
                                                        @RequestParam("maxAge") int maxAge){
        return studentService.findByAgeBetween(minAge, maxAge);
    }
    @GetMapping("/faculty/{id}")
    public Faculty searchForFacultyByStudentId(@PathVariable Long id){
        return studentService.searchForFacultyByStudentId(id);
    }

}
