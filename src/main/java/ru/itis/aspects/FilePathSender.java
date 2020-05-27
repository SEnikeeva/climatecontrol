package ru.itis.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.UserConfirmDto;
import ru.itis.service.SendEmailService;
import ru.itis.service.UploadFileService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class FilePathSender {

    private String email;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private UploadFileService fileService;
    @Pointcut("execution(* ru.itis.service.UploadFileService.saveFile(..))")
    public void selectAllMethodsAvaliable() {

    }
    @Before("selectAllMethodsAvaliable() && args(file, email)")
    public void logStringArguments(MultipartFile file, String email){
        this.email = email;
    }

    @AfterReturning(pointcut = "selectAllMethodsAvaliable()", returning = "someValue")
    public void afterReturningAdvice(Object someValue) {
        Map<String, Object> root = new HashMap<>();
        String[] f = someValue.toString().split("/");
        String filename = "localhost:8070/files/" + f[f.length - 1];
        root.put("email", email);
        root.put("file", filename);
        sendEmailService.sendFileInfo("File info", email, root);
    }


    /*@After(value = "execution(* ru.itis.controller.FilesController.uploadFile(*, *))")
    public void after(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MultipartFile filename = (MultipartFile) args[0];
        Map<String, Object> root = new HashMap<>();
        HttpSession session = (HttpSession) args[1];
        UserConfirmDto user = (UserConfirmDto) session.getAttribute("current_user");
        root.put("email", user);
        String file = "localhost:8088/files/" + filename.getOriginalFilename();
        root.put("file", file);
        sendEmailService.sendFileInfo("File info", user.getEmail(), root);
    }*/


}
