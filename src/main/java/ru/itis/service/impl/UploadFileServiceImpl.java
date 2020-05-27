package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.UserConfirmDto;
import ru.itis.model.Document;
import ru.itis.repository.DocumentRepository;
import ru.itis.repository.UserRepository;
import ru.itis.service.UploadFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


@Component
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private Environment environment;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public String saveFile(MultipartFile file, String email) {

        String name = file.getOriginalFilename();
        String allName = environment.getProperty("storage.path") + "/" + name;
        String type = "." + file.getOriginalFilename().split("\\.")[1];
        try {
            file.transferTo(Paths.get(allName));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }


        Document document = Document.builder().path(file.getOriginalFilename())
                .owner(userRepository.findByEmail(email).get())
                .size(file.getSize())
                .type(type).build();
        documentRepository.save(document);

        return allName;
    }


    @Override
    public File findFile(String fileName) {
        String path = environment.getProperty("storage.path") + "/" + fileName;
        return new File(path);
    }

}
