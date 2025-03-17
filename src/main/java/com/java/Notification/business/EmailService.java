package com.java.Notification.business;

import com.java.Notification.business.dto.TaskDTO;
import com.java.Notification.infrastructure.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${email.send.from}")
    private String from;

    @Value("${email.send.sender}")
    private String sender;

    public void sendEmail(TaskDTO dto){

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(from, sender));
            mimeMessageHelper.setTo(InternetAddress.parse(dto.getUserEmail()));
            mimeMessageHelper.setSubject("Task Notification");

            Context context = new Context();
            context.setVariable("taskName",dto.getTaskName());
            context.setVariable("eventDate",dto.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            context.setVariable("description",dto.getDescription());
            String template = templateEngine.process("notification",context);
            mimeMessageHelper.setText(template, true);
            javaMailSender.send(message);

        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new EmailException("Email not found", e.getCause());
        }

    }



}
