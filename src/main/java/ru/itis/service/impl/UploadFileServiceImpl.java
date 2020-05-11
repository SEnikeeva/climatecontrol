package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.UserConfirmDto;
import ru.itis.service.UploadFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


@Component
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private Environment environment;

    @Override
    public void saveFile(MultipartFile file, UserConfirmDto user) {

        String name = file.getOriginalFilename();
        String allName = environment.getProperty("storage.path") + "/" + name;
        try {
            file.transferTo(Paths.get(allName));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }


    @Override
    public File findFile(String fileName) {
        String path = environment.getProperty("storage.path") + "/" + fileName;
        return new File(path);
    }

}
