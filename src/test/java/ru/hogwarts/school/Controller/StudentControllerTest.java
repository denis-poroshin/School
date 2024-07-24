package ru.hogwarts.school.Controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
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
import org.springframework.data.util.Pair;

import java.util.*;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private AvatarService avatarService;

    @SpyBean
    private StudentService studentService;

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


        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));


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
//        studentObject.put("student", student);
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
    public void searchForStudentsByColorTest() throws Exception{ //No value at JSON path "$.id" Все обшарил в инете, нечего путного не нашел
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
//                .andExpect(jsonPath("$.name").value("Гриффиндор"))
//                .andExpect(jsonPath("$.color").value("Красный"));

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






}
