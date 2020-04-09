package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import ru.itis.service.SendEmailService;
import ru.itis.service.ViewResolver;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class SendEmailServiceImpl implements SendEmailService {

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendCode(String subject, String mail, Map<String, Object> root) {
        String html = viewResolver.process("email_code.ftlh", root);
        MimeMessagePreparator messagePreparator = sendMessage(subject, mail, html);
        mailSender.send(messagePreparator);
    }

    private MimeMessagePreparator sendMessage(String subject, String mail, String html) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            messageHelper.setFrom("Sumbella");
            messageHelper.setTo(mail);
            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
        };
        return messagePreparator;
    }

    @Override
    public void sendFileInfo(String subject, String mail, Map<String, Object> root) {
        String html = viewResolver.process("email_file.ftlh", root);
        MimeMessagePreparator messagePreparator = sendMessage(subject, mail, html);
        mailSender.send(messagePreparator);
    }
}
