package com.java.Notification.controller;

import com.java.Notification.business.EmailService;
import com.java.Notification.business.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody TaskDTO taskDTO){
        emailService.sendEmail(taskDTO);
        return ResponseEntity.ok().build();
    }
}
