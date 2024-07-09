package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }
    public void updateFaculty(long id, Faculty faculty){
        Faculty newFaculty = facultyRepository.findById(id).orElseThrow(
                () -> new FacultyNotFoundException(id));
        newFaculty.setName(faculty.getName());
        newFaculty.setColor(faculty.getColor());
        facultyRepository.save(newFaculty);
    }
    public Faculty getFaculty(long id){
        return facultyRepository.findById(id).orElseThrow(
                () -> new FacultyNotFoundException(id));
    }
    public Faculty removeFaculty(long id){
        Faculty remoteFaculty = facultyRepository.findById(id).orElseThrow(
                () -> new FacultyNotFoundException(id));

        facultyRepository.delete(remoteFaculty);
        return remoteFaculty;

    }
    public Collection<Faculty> getAllFaculty(){
        return Collections.unmodifiableCollection(facultyRepository.findAll()); // создаст неизменяемую копию мапы
    }
    public Collection<Faculty> searchForStudentsByColor(String color){
        return facultyRepository.findAllByColor(color);
    }



}
