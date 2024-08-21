package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //produces требуется тогда когда метод что-то возващает
    public void uploadAvatar(@RequestPart("avatar") MultipartFile multipartFile,
                             @RequestParam long studentId) {
        avatarService.uploadAvatar(multipartFile, studentId);
    }
    @GetMapping()
    public Collection<Avatar> getAvatar(@RequestParam("page") Integer pageNumber,
                                        @RequestParam("size") Integer pageSize){
        return avatarService.getAllAvatars(pageNumber, pageSize);

    }
}
