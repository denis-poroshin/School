package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;

import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student){
        return studentRepository.save(student);
    }
    public void updateStudent(long id, Student student){
        Student newStudent = studentRepository.findById(id).orElseThrow(
                () -> new FacultyNotFoundException(id));
        newStudent.setName(student.getName());
        newStudent.setAge(student.getAge());
        studentRepository.save(newStudent);
    }
    public Student getStudent(long id){
        return studentRepository.findById(id).orElseThrow(
                () -> new StudentNotFoundException(id));
    }
    public Student removeStudent(long id){
        Student remoteStudent = studentRepository.findById(id).orElseThrow(
                () -> new StudentNotFoundException(id));
        studentRepository.delete(remoteStudent);
        return remoteStudent;

    }
    public Collection<Student> getAllStudent(){
        return Collections.unmodifiableCollection(studentRepository.findAll()); // создаст неизменяемую копию мапы
    }
    public Collection<Student> searchForStudentsByAge(int age){
        return studentRepository.findAllByAge(age);
    }
}
