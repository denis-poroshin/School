package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;


public interface FacultyRepository extends JpaRepository<Faculty, Long> {

//    Collection<Faculty> findAllByColor(String color);
    Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
//    @Query("select pos from Faculty , Student where students.f")
//    Collection<Student> findByFOrderByStudents(Long id);
//    @Query(value = "SELECT Student.id, Student.name, Student.age from Faculty , Student where faculty.id = 1 and students.facultu_id = faculty.id")
//    Collection<Student> findByIdS(Long id);




}
