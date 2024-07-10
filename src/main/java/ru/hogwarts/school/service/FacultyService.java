package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;


    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
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
//    public Collection<Faculty> searchForStudentsByColor(String color){
//        return facultyRepository.findAllByColor(color);
//    }
    public Collection<Faculty> findFacultyByNameOrColor(String name, String color){
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
    public Collection<Student> searchForAStudentByFaculty(long id){ // я не знаю как этот метод реализовать правильнее, но он работает)

        return studentRepository.findAll().stream()
                .filter(i -> i.getFaculty().getId() == id)
                .collect(Collectors.toList());

    }
    public Collection<Faculty> getAllFaculty(){
        return Collections.unmodifiableCollection(facultyRepository.findAll()); // создаст неизменяемую копию мапы
    }

}
