package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exceptions.ChangingStudentParametersException;
import ru.hogwarts.school.Exceptions.TheStudentIsAlreadyStudyingAtHogwartsException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private Faculty faculty;
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private long id = 0;
    public Faculty addFaculty(Faculty faculty){
//        if (facultyMap.containsKey(faculty.getId())){
//            throw new TheStudentIsAlreadyStudyingAtHogwartsException("Этот тудент уже учится в Хогвартсе");
//        }
        faculty.setId(++id);
        return facultyMap.put(faculty.getId(), faculty);
    }
    public Faculty changeFaculty(Faculty faculty){
        if (!facultyMap.containsKey(faculty.getId())){
            throw new ChangingStudentParametersException("Студент не найден");
        }
        return facultyMap.put(faculty.getId(), faculty);
    }
    public Faculty getFaculty(long id){
        if (!facultyMap.containsKey(id)){
            throw new ChangingStudentParametersException("Студент не найден");
        }
        return facultyMap.get(id);
    }
    public Faculty removeFaculty(long id){
        if (!facultyMap.containsKey(id)){
            throw new ChangingStudentParametersException("Студент не найден");
        }
        return facultyMap.remove(id);

    }
    public Collection<Faculty> getAllFaculty(){
        return Collections.unmodifiableCollection(facultyMap.values()); // создаст неизменяемую копию мапы
    }
    public Collection<Faculty> searchForStudentsByColor(String color){
        return getAllFaculty().stream()
                .filter(st -> st.getColor().equals(color))
                .collect(Collectors.toList());
    }



}
