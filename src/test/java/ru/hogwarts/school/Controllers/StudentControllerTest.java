package ru.hogwarts.school.Controllers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

//import static com.sun.org.apache.xerces.internal.util.PropertyState.is;

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

        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class));


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

        //Удаляю проверяемого студента чтобы не засорять БД, проверки на удаление нет.
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

        Assertions.assertThat(expextedStudent).isEqualTo(actualStudent);


    }

    @Test
    public void removeStudentTest(){
        ResponseEntity<List> actualEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/student", List.class);
        Student student = new Student(1L, "Гермиона", 11, null);
        Student studentPost = this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        testRestTemplate.delete("http://localhost:" + port + "/student/" + studentPost.getId(), Student.class);
        ResponseEntity<List> expectedEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/student", List.class);

        Assertions.assertThat(expectedEntity).isEqualTo(actualEntity);


    }

    @Test
    public void getAllStudentTest(){
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/student", List.class);
        System.out.println(responseEntity);

        Assertions.assertThat(responseEntity).isNotNull();
    }
    @Test
    public void searchForStudentsByAge(){
        ResponseEntity<Student> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/?age=11", Student.class);

        Assertions.assertThat(responseEntity).isNotNull();
    }

    @Test
    public void findStudentsByAgeBetweenTest(){
        ResponseEntity<Student> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/?minAge=0&maxAge=25", Student.class);

        Assertions.assertThat(responseEntity).isNotNull();
    }
    @Test
    public void searchForFacultyByStudentIdTest(){

        ResponseEntity<Faculty> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/2", Faculty.class);
        Assertions.assertThat(responseEntity).isNotNull();


    }
    @Test
    public void getAvatarFromDbTest(){
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();

        parameters.add("file", new org.springframework.core.io.ClassPathResource("image.jpg"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<LinkedMultiValueMap<String, Object>>(parameters, headers);

        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/1/avatar-from-db", HttpMethod.GET, entity, String.class);
        Assertions.assertThat(response.getStatusCode().is2xxSuccessful());

    }
    @Test
    public void getAvatarFromFsTest(){
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();

        parameters.add("file", new org.springframework.core.io.ClassPathResource("image.jpg"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<LinkedMultiValueMap<String, Object>>(parameters, headers);

        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/1/avatar-from-db", HttpMethod.GET, entity, String.class);
        Assertions.assertThat(response.getStatusCode().is2xxSuccessful());

    }
}
