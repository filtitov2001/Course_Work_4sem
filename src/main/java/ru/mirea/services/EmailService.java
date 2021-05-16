package ru.mirea.services;


import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class EmailService implements NotifyService{

    JavaMailSender sender;
    @Value("${spring.mail.username}")
    private String username;



    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }


    @Async
    @Override
    public void send(String message, String mailTo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(mailTo,username);
        mailMessage.setSubject("Принят заказ");
        mailMessage.setText(message);
        sender.send(mailMessage);
    }

}
