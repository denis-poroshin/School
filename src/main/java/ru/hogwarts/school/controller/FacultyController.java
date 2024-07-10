package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);
    }
    @PutMapping("/{id}")
    public void changeFaculty(@PathVariable long id, @RequestBody Faculty faculty){
        facultyService.updateFaculty(id, faculty);
    }
    @GetMapping("/{id}")
    public Faculty getFaculty(@PathVariable long id){
        return facultyService.getFaculty(id);
    }
    @DeleteMapping("/{id}")
    public Faculty removeFaculty(@PathVariable long id){
        return facultyService.removeFaculty(id);
    }
    @GetMapping
    public Collection<Faculty> getAllFaculty(){
        return facultyService.getAllFaculty();
    }
    @GetMapping("/")
    public Collection<Faculty> searchForStudentsByColor(@RequestParam(value = "name", required = false) String name,
                                                        @RequestParam(value = "color", required = false) String color){
        return facultyService.findFacultyByNameOrColor(name, color);
    }
    @GetMapping("/student{id}")
    public Collection<Student> searchForAStudentByFaculty(@PathVariable long id){
        return facultyService.searchForAStudentByFaculty(id);
    }
//    @GetMapping
//    public Collection<Faculty> findFacultyByNameOrColor(@RequestParam(value = "name", required = false) String name,
//                                                        @RequestParam(value = "color", required = false) String color){
//        return facultyService.findFacultyByNameOrColor(name, color);
//    }
}
