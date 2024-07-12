package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findAllByAge(int age);
    Collection<Student> findByAgeBetween(int minAge, int magAge);
    Collection<Student>findAllByFaculty_Id(long id);
    // можно с SQL запросом
//    @Query("SELECT * FROM student WHERE faculty_id = :facultyId")
//    Collection<Student>findAllByFacultyId(@Param("facultyId") long facultyId);
}
