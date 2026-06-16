package com.larryDev.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.Properties;

@Component
public class ClaseEmailService {

    @Value("${email.usuario}")
    private String remitente;
    @Value("${email.password}")
    private String contraseña;

    public  void enviarEmail(String nombreFamiliar, String email , String nombreAmigo) {

        Properties props = new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.starttls.required","true");
        props.put("mail.smtp.ssl.protocols","TLSv1.2");
        props.put("mail.smtp.ssl.trust","smtp.gmail.com");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(remitente , contraseña);
            }
        });
        //Invocamos el metodo del cliente
        montarEmail(session, email, remitente,mensajeCliente(nombreFamiliar,nombreAmigo));

    }

    private void montarEmail(Session session, String email, String remitente, String asunto){
        //Iniciamos session.
        Message mensaje = new MimeMessage(session);
        //Punto de partida del emial (emisor)
        try {
            mensaje.setFrom(new InternetAddress(remitente));
            //Aqui enviaremos el email, con el amigo que le ha tocado.
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            mensaje.setSubject("Amigo Invisible 🎁");
            mensaje.setText(asunto);
            Transport.send(mensaje);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private  String mensajeCliente(String nombreFamiliar, String nombreAmigo){
        return  nombreFamiliar+ ", te ha tocado regalarle a: "+nombreAmigo;
    }
}
