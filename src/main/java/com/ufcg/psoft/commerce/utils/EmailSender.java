package com.ufcg.psoft.commerce.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;
    
    public void sendEmail(String destino, String mensagem){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("expressopitsa@gmail.com");
        message.setTo(destino);
        message.setText(mensagem);
        message.setSubject("Status Expresso PitsA");

        mailSender.send(message);
    }
}
