package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarProcessingException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import org.springframework.data.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AvatarService {
//    @Value("${student.avatars.dir.path}")
//    private String avatarDir;
    private final Path path;

    private final StudentRepository studentRepository;

    private final AvatarRepository avatarRepository;

    public AvatarService(StudentRepository studentRepository,
                         AvatarRepository avatarRepository,
                         @Value("${student.avatars-dir-path-name}") String avatarDirName) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
        path = Paths.get(avatarDirName);
    }

    @Transactional
    public void uploadAvatar(MultipartFile multipartFile, long studentId) {
        try {
            byte[] data = multipartFile.getBytes();
            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            Path avatarPath = path.resolve(UUID.randomUUID() + "." + extension);
            Files.write(avatarPath, data);
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentNotFoundException(studentId));
            Avatar avatar = avatarRepository.findByStudent_Id(studentId)
                    .orElseGet(Avatar::new);
            avatar.setStudent(student);
            avatar.setData(data);
            avatar.setFileSize(data.length);
            avatar.setMediaType(multipartFile.getContentType());
            avatar.setFilePath(avatarPath.toString());
            avatarRepository.save(avatar);

        } catch (IOException e) {
            throw new AvatarProcessingException();
        }
    }
    public Pair<byte[], String> getAvatarFromDb(long studentId){
        Avatar avatar = avatarRepository.findByStudent_Id(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        return Pair.of(avatar.getData(), avatar.getMediaType());

    }
    public Pair<byte[], String> getAvatarFromFs(long studentId){
        try {
            Avatar avatar = avatarRepository.findByStudent_Id(studentId)
                    .orElseThrow(() -> new StudentNotFoundException(studentId));
            return Pair.of(Files.readAllBytes(Path.of(avatar.getFilePath())), avatar.getMediaType());
        }catch (IOException e){
            throw new AvatarProcessingException();
        }


    }

}
