package ru.itis.service;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.UserConfirmDto;

import java.io.File;

public interface UploadFileService {
    String saveFile(MultipartFile file,String email);

    File findFile(String fileName);
}
