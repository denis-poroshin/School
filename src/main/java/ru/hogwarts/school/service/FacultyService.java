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
        facultyRepository.findById(id).orElseThrow(
                () -> new FacultyNotFoundException(id));
        faculty.setId(id);
        facultyRepository.save(faculty);
    }
    public Faculty getFaculty(long id){
        return facultyRepository.findById(id).orElseThrow(
                () -> new FacultyNotFoundException(id));
    }
    public Faculty removeFaculty(long id){
        Faculty remoteFaculty = facultyRepository.findById(id).orElseThrow(
                () -> new FacultyNotFoundException(id));

        facultyRepository.deleteById(id);
        return remoteFaculty;

    }
    public Collection<Faculty> getAllFaculty(){
        return Collections.unmodifiableCollection(facultyRepository.findAll()); // создаст неизменяемую копию мапы
    }
//    public Collection<Faculty> searchForStudentsByColor(String color){
//        return getAllFaculty().stream()
//                .filter(st -> st.getColor().equals(color))
//                .collect(Collectors.toList());
//    }



}
