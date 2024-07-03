package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.Exceptions.ChangingStudentParametersException;
import ru.hogwarts.school.Exceptions.TheStudentIsAlreadyStudyingAtHogwartsException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StudentServiceTest {
    Student student;
    StudentService studentServiceMock = new StudentService();

    Student studentOne = new Student(1L, "Ron", 15);
    Student studentTwo = new Student(2L, "Germinal", 18);
    List<Student> listStudent = new ArrayList<>(List.of(studentOne, studentTwo));
    @Test
    public void addTest(){

        studentServiceMock.addStudent(studentOne);
        studentServiceMock.addStudent(studentTwo);
        int expected = 2;
        int actual = studentServiceMock.getAllStudent().size();
        Assertions.assertEquals(expected, actual);
        org.assertj.core.api.Assertions.assertThat(studentServiceMock.getAllStudent().containsAll(listStudent));
    }
    @Test
    public void changeStudentTest(){
        studentServiceMock.addStudent(studentOne);
        studentServiceMock.addStudent(studentTwo);
        Student actualStudent = new Student(1L, "Garri", 86);

        List<Student> actualList = new ArrayList<>(List.of(actualStudent, studentTwo));
        studentServiceMock.changeStudent(actualStudent);

        org.assertj.core.api.Assertions.assertThat(studentServiceMock.getAllStudent().containsAll(actualList));
    }
//    @Test
//    public void changeStudentExceptionTest(){
//        studentServiceMock.addStudent(studentOne);
//        studentServiceMock.addStudent(studentTwo);
//        Student studentTree = new Student(5L, "Garri", 86);
//        Assertions.assertThrows(ChangingStudentParametersException.class,
//                studentServiceMock.changeStudent(new Student()));
//    }
    @Test
    public void getStudentTest(){
        studentServiceMock.addStudent(studentOne);
        studentServiceMock.addStudent(studentTwo);
        Student actual = studentOne;
        Student expected = studentServiceMock.getStudent(1);
        Assertions.assertEquals(expected, studentOne);
    }
    @Test
    public void removeStudentTest(){
        studentServiceMock.addStudent(studentOne);
        studentServiceMock.addStudent(studentTwo);
        Assertions.assertEquals(studentServiceMock.removeStudent(1), studentOne);

        int expected = 1;
        int actual = studentServiceMock.getAllStudent().size();

        Assertions.assertEquals(expected, actual);

    }
    @Test
    public void getAllStudentTest(){
        studentServiceMock.addStudent(studentOne);
        studentServiceMock.addStudent(studentTwo);

        org.assertj.core.api.Assertions.assertThat(studentServiceMock.getAllStudent().containsAll(listStudent));
        Assertions.assertEquals(studentServiceMock.getAllStudent().size(), listStudent.size());
    }
    @Test
    public void searchForStudentsByAgeTest(){
        studentServiceMock.addStudent(studentOne);
        studentServiceMock.addStudent(studentTwo);
        List<Student> expected = new ArrayList<>(List.of(studentOne));
        List<Student> actual = (List<Student>) studentServiceMock.searchForStudentsByAge(15);
        Assertions.assertEquals(expected, actual);

    }

}
