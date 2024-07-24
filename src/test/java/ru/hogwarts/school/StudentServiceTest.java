//package ru.hogwarts.school;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.repositories.StudentRepository;
//import ru.hogwarts.school.service.StudentService;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//public class StudentServiceTest {
//    Student student;
//    StudentRepository studentRepository;
//    StudentService studentServiceMock = new StudentService(studentRepository);
//
//    Student studentOne = new Student(1L, "Ron", 15);
//    Student studentTwo = new Student(2L, "Germinal", 18);
//    List<Student> listStudent = new ArrayList<>(List.of(studentOne, studentTwo));
//    @Test
//    public void addTest(){
//
//        studentServiceMock.createStudent(studentOne);
//        studentServiceMock.createStudent(studentTwo);
//        int expected = 2;
//        int actual = studentServiceMock.getAllStudent().size();
//        Assertions.assertEquals(expected, actual);
//        org.assertj.core.api.Assertions.assertThat(studentServiceMock.getAllStudent().containsAll(listStudent));
//        System.out.println(actual);
//    }
//    @Test
//    public void changeStudentTest(){
//        studentServiceMock.createStudent(studentOne);
//        studentServiceMock.createStudent(studentTwo);
//        Student actualStudent = new Student(1L, "Garri", 86);
//
//        List<Student> actualList = new ArrayList<>(List.of(actualStudent, studentTwo));
//        studentServiceMock.createStudent(actualStudent);
//
//        org.assertj.core.api.Assertions.assertThat(studentServiceMock.getAllStudent().containsAll(actualList));
//    }
////    @Test
////    public void changeStudentExceptionTest(){
////        studentServiceMock.addStudent(studentOne);
////        studentServiceMock.addStudent(studentTwo);
////        Student studentTree = new Student(5L, "Garri", 86);
////        Assertions.assertThrows(ChangingStudentParametersException.class,
////                studentServiceMock.changeStudent(studentOne));
////    }
//    @Test
//    public void getStudentTest(){
//        studentServiceMock.createStudent(studentOne);
//        studentServiceMock.createStudent(studentTwo);
//        Student actual = studentOne;
//        Student expected = studentServiceMock.getStudent(1);
//        Assertions.assertEquals(expected, studentOne);
//    }
//    @Test
//    public void removeStudentTest(){
//        studentServiceMock.createStudent(studentOne);
//        studentServiceMock.createStudent(studentTwo);
//        Assertions.assertEquals(studentServiceMock.removeStudent(1), studentOne);
//
//        int expected = 1;
//        int actual = studentServiceMock.getAllStudent().size();
//
//        Assertions.assertEquals(expected, actual);
//
//    }
//    @Test
//    public void getAllStudentTest(){
//        studentServiceMock.createStudent(studentOne);
//        studentServiceMock.createStudent(studentTwo);
//
//        org.assertj.core.api.Assertions.assertThat(studentServiceMock.getAllStudent().containsAll(listStudent));
//        Assertions.assertEquals(studentServiceMock.getAllStudent().size(), listStudent.size());
//    }
////    @Test
////    public void searchForStudentsByAgeTest(){
////        studentServiceMock.createStudent(studentOne);
////        studentServiceMock.createStudent(studentTwo);
////        List<Student> expected = new ArrayList<>(List.of(studentOne));
////        List<Student> actual = (List<Student>) studentServiceMock.searchForStudentsByAge(15);
////        Assertions.assertEquals(expected, actual);
////
////    }
//
//}
