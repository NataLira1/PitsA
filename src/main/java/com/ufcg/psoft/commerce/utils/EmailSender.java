package com.ufcg.psoft.commerce.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender sender;

    private static JavaMailSender mailSender;

    @PostConstruct
    public void init(){
        mailSender = sender;
    }
    
    public static void sendEmail(String destino, String mensagem){
        if(destino == null || mensagem == null) return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("expressopitsa@gmail.com");
        message.setTo(destino);
        message.setText(mensagem);
        message.setSubject("Status Expresso PitsA");

        mailSender.send(message);
    }
}
