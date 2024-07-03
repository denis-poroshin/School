package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

public class FacultyServiceTest {
    Faculty faculty;
    FacultyService facultyServiceTest = new FacultyService();
    Faculty facultyOne = new Faculty(1L, "Ron", "Rad");
    Faculty facultyTwo = new Faculty(2L, "Germinal", "black");
    List<Faculty> listStudent = new ArrayList<>(List.of(facultyOne, facultyTwo));

    @Test
    public void addTest(){

        facultyServiceTest.addFaculty(facultyOne);
        facultyServiceTest.addFaculty(facultyTwo);
        int expected = 2;
        int actual = facultyServiceTest.getAllFaculty().size();
        Assertions.assertEquals(expected, actual);
        org.assertj.core.api.Assertions.assertThat(facultyServiceTest.getAllFaculty().containsAll(listStudent));
    }
    @Test
    public void changeStudentTest(){
        facultyServiceTest.addFaculty(facultyOne);
        facultyServiceTest.addFaculty(facultyTwo);
        Faculty actualStudent = new Faculty(1L, "Garri", "white");

        List<Faculty> actualList = new ArrayList<>(List.of(actualStudent, facultyTwo));
        facultyServiceTest.changeFaculty(actualStudent);

        org.assertj.core.api.Assertions.assertThat(facultyServiceTest.getAllFaculty().containsAll(actualList));
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
        facultyServiceTest.addFaculty(facultyOne);
        facultyServiceTest.addFaculty(facultyTwo);
        Faculty actual = facultyOne;
        Faculty expected = facultyServiceTest.getFaculty(1);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void removeStudentTest(){
        facultyServiceTest.addFaculty(facultyOne);
        facultyServiceTest.addFaculty(facultyTwo);
        Assertions.assertEquals(facultyServiceTest.removeFaculty(1), facultyOne);

        int expected = 1;
        int actual = facultyServiceTest.getAllFaculty().size();

        Assertions.assertEquals(expected, actual);

    }
    @Test
    public void getAllStudentTest(){
        facultyServiceTest.addFaculty(facultyOne);
        facultyServiceTest.addFaculty(facultyTwo);

        org.assertj.core.api.Assertions.assertThat(facultyServiceTest.getAllFaculty().containsAll(listStudent));
        Assertions.assertEquals(facultyServiceTest.getAllFaculty().size(), listStudent.size());
    }
    @Test
    public void searchForStudentsByAgeTest(){
        facultyServiceTest.addFaculty(facultyOne);
        facultyServiceTest.addFaculty(facultyTwo);
        List<Faculty> expected = new ArrayList<>(List.of(facultyOne));
        List<Faculty> actual = (List<Faculty>) facultyServiceTest.searchForStudentsByColor("Rad");
        Assertions.assertEquals(expected, actual);

    }


}
