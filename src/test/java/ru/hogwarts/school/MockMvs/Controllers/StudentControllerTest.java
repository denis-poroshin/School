package ru.hogwarts.school.MockMvs.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private StudentService studentService;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;


    @MockBean
    private StudentRepository studentRepository;

    //////////////////////
    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private AvatarService avatarService;



    @InjectMocks
    private StudentController studentController;

    @Test
    public void addStudentTest() throws Exception{
//        Faculty faculty = new Faculty(1L, "Гриффиндор", "Красный");
        Student student = new Student(1L, "Гарри", 11, null);
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "Гарри");
        studentObject.put("age", 11);
        studentObject.put("faculty", null);


        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Гарри"))
                .andExpect(jsonPath("$.age").value("11"));
//                .andExpect(jsonPath("$.faculty").value(null));

    }
    @Test
    public void updateTest() throws Exception{
        Student student = new Student(1L, "Гарри", 11, null);
        Student newStudent = new Student(1L, "Гермиона", 11, null);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "Гермиона");
        studentObject.put("age", 11);
        studentObject.put("faculty", null);


        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/1")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders //проверяем, что студент изменился
                        .get("/student/1")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Гермиона"))
                .andExpect(jsonPath("$.age").value("11"));


    }
//    @Test
//    public void updateTest1() throws Exception{
//        long id = 1L;
//        String newName = "Гермиона";
//        int newAge = 21;
//
//        Student oldStudent = new Student();
//        oldStudent.setId(id);
//        oldStudent.setName("Гарри");
//        oldStudent.setAge(11);
//        oldStudent.setFaculty(null);
//
//        Student newStudent = new Student();
//        newStudent.setId(id);
//        newStudent.setName(newName);
//        newStudent.setAge(newAge);
//        newStudent.setFaculty(null);
//
//        when(studentRepository.findById(any())).thenReturn(Optional.of(oldStudent));
//        when(studentRepository.save(any())).thenReturn(newStudent);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.put("/student/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .content(objectMapper.writeValueAsString(newStudent))
//        ).andExpect(result -> {
//            MockHttpServletResponse response = result.getResponse();
//            Student responseStudent = objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), Student.class);
//            Assertions.assertThat(responseStudent).usingRecursiveComparison().isEqualTo(newStudent);
//            Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        });
//
//
//    }

    @Test
    public void getStudentTest() throws Exception{

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "Гарри");
        studentObject.put("age", 11);
        studentObject.put("faculty", null);


        Student student = new Student(1L, "Гарри", 11, null);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Гарри"))
                .andExpect(jsonPath("$.age").value("11"));
    }
    @Test
    public void removeStudentTest() throws Exception{

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "Гарри");
        studentObject.put("age", 11);
        studentObject.put("faculty", null);


        Student student = new Student(1L, "Гарри", 11, null);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
//        when(studentRepository.delete(any(Student.class))).thenReturn(Optional.of(student)) //не знаю как правильно приобразовать


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Гарри"))
                .andExpect(jsonPath("$.age").value("11"));
    }
    @Test
    public void getAllStudentTest() throws Exception{
        Student student = new Student(1L, "Гарри", 11, null);

        JSONObject studentObject = new JSONObject();

        ArrayList<Student> arrayList = new ArrayList<>(List.of(student));

        when(studentRepository.findAll()).thenReturn(arrayList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(arrayList));
    }
    @Test
    public void searchForStudentsByColorTest() throws Exception{
        Student student = new Student(1L, "Гарри", 11, null);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "Гарри");
        studentObject.put("age", 11);
        studentObject.put("faculty", null);


        when(studentRepository.findByAgeBetween(0, 11)).thenReturn(Stream.of(student).toList());


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?minAge=1&maxAge=15")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Гарри"))
                .andExpect(jsonPath("$.age").value(11));
    }
    @Test
    public void searchForFacultyByStudentIdTest() throws Exception{
        Faculty faculty = new Faculty(1L, "Гриффиндор", "Красный");
        Student student = new Student(1L, "Гарри", 11, faculty);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "Гарри");
        studentObject.put("age", 11);
        studentObject.put("faculty", faculty);


        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1/faculty")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Гриффиндор"))
                .andExpect(jsonPath("$.color").value("Красный"));
    }
    @Test
    public void getAvatarFromDbTest() throws Exception {
        Student student = new Student(1L, "Гарри", 11, null);
        byte[] data = {1, 2, 3};
        Avatar avatar = new Avatar(1L, "dwa", 123L, "jpeg", data, student);
        Pair<byte[], String> pair = Pair.of(data, "jpeg");
        JSONObject studentObject = new JSONObject();

        when(studentRepository.save(student)).thenReturn(student);
        when(avatarRepository.findByStudent_Id(1)).thenReturn(Optional.of(avatar));
        when(avatarService.getAvatarFromDb(1)).thenReturn(pair);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1/avatar-from-fs")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(pair));


    }
    @Test
    public void getAvatarFromFsTest() throws Exception{
        Student student = new Student(1L, "Гарри", 11, null);
        byte[] data = {1, 2, 3};
        Avatar avatar = new Avatar(1L, "dwa", 123L, "jpeg", data, student);
        Pair<byte[], String> pair = Pair.of(data, "jpeg");
        JSONObject studentObject = new JSONObject();

        when(studentRepository.save(student)).thenReturn(student);
        when(avatarRepository.findByStudent_Id(1)).thenReturn(Optional.of(avatar));
        when(avatarService.getAvatarFromDb(1)).thenReturn(pair);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1/avatar-from-fs")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(pair));
    }
    @Test
    public void getNumberOfStudents(){

    }






}

