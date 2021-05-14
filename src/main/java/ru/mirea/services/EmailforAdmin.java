package ru.mirea.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class EmailforAdmin implements AdminService {

    JavaMailSender sender;
    @Value("${spring.mail.username}")
    private String username;

    public EmailforAdmin(JavaMailSender sender) {
        this.sender = sender;
    }

    @Async
    @Override
    public void sendToAdmin(String password) {
        SimpleMailMessage passwordMessage = new SimpleMailMessage();
        passwordMessage.setFrom(username);
        passwordMessage.setTo(username);
        passwordMessage.setSubject("Новый пароль");
        passwordMessage.setText(password);
        sender.send(passwordMessage);

    }
}
