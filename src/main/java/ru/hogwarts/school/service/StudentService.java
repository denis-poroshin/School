package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;

import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;


    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }


    public Student createStudent(Student student){
        Faculty faculty = null;
        if(student.getFaculty() != null && student.getFaculty().getId() != null){
            faculty = facultyRepository.findById(student.getFaculty().getId())
                    .orElseThrow(() -> new FacultyNotFoundException(student.getFaculty().getId()));
        }
        student.setFaculty(faculty);
        student.setId(null);
        return studentRepository.save(student);
    }
    public void updateStudent(long id, Student student){
        Student newStudent = studentRepository.findById(id).orElseThrow(
                () -> new StudentNotFoundException(id));
        Faculty faculty = null;
        if(student.getFaculty() != null && student.getFaculty().getId() != null){
            faculty = facultyRepository.findById(student.getFaculty().getId())
                    .orElseThrow(() -> new FacultyNotFoundException(student.getFaculty().getId()));
        }

        newStudent.setName(student.getName());
        newStudent.setAge(student.getAge());
        newStudent.setFaculty(faculty);
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
        return Collections.unmodifiableCollection(studentRepository.findAll()); // создаст неизменяемую копию листа
    }
    public Collection<Student> findByAgeBetween(int minAge, int maxAge){
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }
    public Faculty searchForFacultyByStudentId(long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
        return student.getFaculty();
    }
    public Collection<Student> searchForStudentsByAge(int age){
        return studentRepository.findAllByAge(age);
    }
    public Integer getNumberOfStudents(){
        return studentRepository.getNumberOfStudents();
    }
    public Double getMiddleAged(){
        return studentRepository.getMiddleAged();
    }
    public Collection<Student> getNewFiveStudents(){
        return studentRepository.getNewFiveStudents();
    }

}
