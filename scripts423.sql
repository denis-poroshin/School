SELECT student.name, student.age, faculty.name, faculty.color
FROM student
         INNER JOIN faculty ON faculty.id = student.facultu_id;



SELECT student.name, student.age
FROM student
         INNER JOIN avatar ON student.id = avatar.student_id;
