package ru.itis.service;

import java.util.Map;

public interface SendEmailService {

    void sendCode(String subject, String mail, Map<String, Object> model);
    void sendFileInfo(String subject, String mail, Map<String, Object> model);
}
