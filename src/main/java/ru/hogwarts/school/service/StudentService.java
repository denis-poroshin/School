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
public class StudentService {
    private Student student;
    private final Map<Long, Student> studentMap = new HashMap<>();
    private long id = 0;

    public Student addStudent(Student student){
//        if (studentMap.containsKey(student.getId())){
//            throw new TheStudentIsAlreadyStudyingAtHogwartsException("Этот тудент уже учится в Хогвартсе");
//        }
        student.setId(++id);
        return studentMap.put(student.getId(), student);
    }
    public Student changeStudent(Student student){
        if (!studentMap.containsKey(student.getId())){
            throw new ChangingStudentParametersException("Студент не найден");
        }
        studentMap.put(student.getId(), student);
        return student;
    }
    public Student getStudent(long id){
        if (!studentMap.containsKey(id)){
            throw new ChangingStudentParametersException("Студент не найден");
        }
        return studentMap.get(id);
    }
    public Student removeStudent(long id){
        if (!studentMap.containsKey(id)){
            throw new ChangingStudentParametersException("Студент не найден");
        }
        return studentMap.remove(id);

    }
    public Collection<Student> getAllStudent(){
        return Collections.unmodifiableCollection(studentMap.values()); // создаст неизменяемую копию мапы
    }
    public Collection<Student> searchForStudentsByAge(int age){
        return getAllStudent().stream()
                .filter(st -> st.getAge() == age)
                .collect(Collectors.toList());
    }
}
