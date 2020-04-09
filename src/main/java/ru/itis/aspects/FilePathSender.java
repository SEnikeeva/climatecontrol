package ru.itis.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.Dto.UserConfirmDto;
import ru.itis.service.SendEmailService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class FilePathSender {

    @Autowired
    private SendEmailService sendEmailService;


    @After(value = "execution(* ru.itis.controller.FilesController.uploadFile(*, *))")
    public void after(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MultipartFile filename = (MultipartFile) args[0];
        Map<String, Object> root = new HashMap<>();
        HttpSession session = (HttpSession) args[1];
        UserConfirmDto user = (UserConfirmDto) session.getAttribute("current_user");
        root.put("user", user);
        String file = "localhost:8088/files/" + filename.getOriginalFilename();
        root.put("file", file);
        sendEmailService.sendFileInfo("File info", user.getEmail(), root);
    }


}
