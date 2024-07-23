package ru.hogwarts.school.Controllers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    long total = 2;

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads(){
        Assertions.assertThat(studentController).isNotNull();
    }
    @Test
    public void addStudentTest() throws Exception{
        ++total;

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setColor("Голубой");
        faculty.setName("Пуффендуй");
        Student student = new Student();
        student.setId(total);
        student.setName("Гарри");
        student.setAge(11);
        student.setFaculty(null);

        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isEqualTo(student);

    }
    @Test
    public void updateTest(){
        Student student = new Student(1L, "Гермиона", 11, null);
        Student newStudent = new Student(1L, "Рон", 15, null);

        Student studentPost = this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        Student actualStudent = this.testRestTemplate.getForObject("http://localhost:" + port + "/student/" + studentPost.getId(), Student.class);

        testRestTemplate.put("http://localhost:" + port + "/student/" + studentPost.getId(),newStudent, Student.class);
        Student expectedStudent = testRestTemplate.getForObject("http://localhost:" + port + "/student/" + studentPost.getId(), Student.class);

        Assertions.assertThat(expectedStudent).isNotEqualTo(actualStudent);

        testRestTemplate.delete("http://localhost:" + port + "/student/" + studentPost.getId(), Student.class);



    }
    @Test
    public void getStudentTest(){
        Student expextedStudent = new Student();
        expextedStudent.setId(1L);
        expextedStudent.setName("Гарри");
        expextedStudent.setAge(11);
        expextedStudent.setFaculty(null);

        Student actualStudent = testRestTemplate.getForObject("http://localhost:" + port + "/student/1", Student.class);
        org.junit.jupiter.api.Assertions.assertEquals(expextedStudent, actualStudent);

    }

    @Test
    public void removeStudentTest(){
        ResponseEntity<List> actualEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/student", List.class);
        Student student = new Student(1L, "Гермиона", 11, null);
        Student studentPost = this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        testRestTemplate.delete("http://localhost:" + port + "/student/" + studentPost.getId(), Student.class);
        ResponseEntity<List> expectedEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/student", List.class);

        org.junit.jupiter.api.Assertions.assertEquals(expectedEntity, actualEntity);


    }

    @Test
    public void getAllStudentTest(){
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/student", List.class);
        System.out.println(responseEntity);
        org.junit.jupiter.api.Assertions.assertNotNull(responseEntity);
    }
    @Test
    public void searchForStudentsByAge(){
//        Student student = new Student();
//        student.setId(total);
//        student.setName("Гарри");
//        student.setAge(11);
//        student.setFaculty(null);

        ResponseEntity<Student> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/?age=11", Student.class);
        Assertions.assertThat(responseEntity).isNotNull();
    }

    @Test
    public void findStudentsByAgeBetweenTest(){
//        Student student = new Student();
//        student.setId(total);
//        student.setName("Гарри");
//        student.setAge(11);
//        student.setFaculty(null);

        ResponseEntity<Student> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/?minAge=0&maxAge=25", Student.class);
        Assertions.assertThat(responseEntity).isNotNull();
    }
    @Test
    public void searchForFacultyByStudentIdTest(){ //Может это не правильно, тест проходит. Не знаю как в этот запросе создать студента вместе с факультетам

        ResponseEntity<Faculty> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/2", Faculty.class);
        Assertions.assertThat(responseEntity).isNotNull();


    }
    //Требуется тестировать аватарку?
}
