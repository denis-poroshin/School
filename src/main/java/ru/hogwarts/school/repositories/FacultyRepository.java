package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;


public interface FacultyRepository extends JpaRepository<Faculty, Long> {

//    Collection<Faculty> findAllByColor(String color);
    Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
//    Collection<Faculty>findAllByFaculty_Id(long facultyId);






}
