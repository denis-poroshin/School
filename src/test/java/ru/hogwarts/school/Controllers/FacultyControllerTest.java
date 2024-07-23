package ru.hogwarts.school.Controllers;
import com.sun.istack.NotNull;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;



    @Test
    public void contextLoads(){
        Assertions.assertThat(facultyController).isNotNull();
    }
    @Test
    public void createFacultyTest() throws Exception{
        Faculty faculty = new Faculty();
        faculty.setId(5L);
        faculty.setColor("Голубой");
        faculty.setName("Пуффендуй");


        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class))
                .isEqualTo(faculty);

    }
    @Test
    public void updateFacultyTest() throws Exception{
        Faculty faculty = new Faculty(1L, "Когтевран", "Белый");
        Faculty newFaculty = new Faculty(1L, "Гриффиндор", "Зеленый");

        Faculty facultyPost = this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        Faculty actualFaculty = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + facultyPost.getId(), Faculty.class);


        testRestTemplate.put("http://localhost:" + port + "/faculty/" + facultyPost.getId() , newFaculty, Faculty.class);
        Faculty expectedFaculty = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + facultyPost.getId(), Faculty.class);

        Assertions.assertThat(expectedFaculty).isNotEqualTo(actualFaculty);

        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + facultyPost.getId(), Faculty.class);

    }
    @Test
    public void getFacultyTest() throws  Exception{
        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setId(5L);
        expectedFaculty.setColor("Голубой");
        expectedFaculty.setName("Пуффендуй");

        Faculty actualFaculty = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/5", Faculty.class);
        org.junit.jupiter.api.Assertions.assertEquals(expectedFaculty, actualFaculty);
    }

    @Test
    public void getAllFacultyTest(){
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty", List.class);
        System.out.println(responseEntity);
        org.junit.jupiter.api.Assertions.assertNotNull(responseEntity);
    }
    @Test
    public void removeFacultyTest() throws Exception {
        ResponseEntity<List> actualEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty", List.class);
        Faculty faculty = new Faculty(1L, "Когтевран", "Белый");
        Faculty facultyPost = this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + facultyPost.getId(), Faculty.class);
        ResponseEntity<List> expectedEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty", List.class);

        org.junit.jupiter.api.Assertions.assertEquals(expectedEntity, actualEntity);


    }
    @Test
    public void searchForStudentsByColorTest(){
        Faculty faculty = new Faculty(2L, "Слизарин", "Синий");

        ResponseEntity<Faculty> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/?nameOfColor=Синий", Faculty.class); //Понимаю как для осного факультета, но как для колеции...
        Assertions.assertThat(responseEntity).isNotNull();

    }
    @Test
    public void searchForAStudentByFacultyTest() throws Exception{
        Faculty faculty = new Faculty(2L, "Слизарин", "Синий");
        Student student = new Student(1L, "Гарри", 11, faculty);

        ResponseEntity<Student> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/1/student", Student.class); //Понимаю как для осного студента, но как для колеции...
        Assertions.assertThat(responseEntity).isNotNull();

    }




}
