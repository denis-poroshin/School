package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

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
    @GetMapping("/color")
    public Collection<Faculty> searchForStudentsByColor(@RequestParam("color") String color){
        return facultyService.searchForStudentsByColor(color);
    }
}
