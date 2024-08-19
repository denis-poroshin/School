package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(FacultyService.class);


    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public Faculty createFaculty(Faculty faculty){
        logger.info("Faculty created : {}", faculty);
        return facultyRepository.save(faculty);
    }
    public void updateFaculty(long id, Faculty faculty){
        logger.info("Faculty updated : id = {}, faculty = {}", id, faculty);
        Faculty newFaculty = facultyRepository.findById(id).orElseThrow(
                () -> new FacultyNotFoundException(id));
        newFaculty.setName(faculty.getName());
        newFaculty.setColor(faculty.getColor());
        facultyRepository.save(newFaculty);
    }
    public Faculty getFaculty(long id){
        logger.info("Get faculty : id = {}", id);
        return facultyRepository.findById(id).orElseThrow(
                () -> new FacultyNotFoundException(id));
    }
    public Faculty removeFaculty(long id){
        logger.info("Remove faculty : id = {}", id);
        Faculty remoteFaculty = facultyRepository.findById(id).orElseThrow(
                () -> new FacultyNotFoundException(id));

        facultyRepository.delete(remoteFaculty);
        return remoteFaculty;

    }

    public Collection<Faculty> findFacultyByNameOrColor(String nameOfColor){
        logger.info("Find faculty by name or color : nameOfColor = {}", nameOfColor);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(nameOfColor , nameOfColor);
    }

    public String getMaxLengthNameFaculty(){
        logger.info("Get max length name faculty");
        int maxName = 0;

        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse("Факультетов не существует");

    }

    public Collection<Student> searchForAStudentByFaculty(long id) {
        logger.info("Get student by id : {}", id);
        return studentRepository.findAllByFaculty_Id(id);
    }


    public Collection<Faculty> getAllFaculty(){
        logger.info("Get all faculty");
        return Collections.unmodifiableCollection(facultyRepository.findAll()); // создаст неизменяемую копию коллекции
    }

}
