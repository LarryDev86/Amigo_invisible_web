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
        montarEmail(session, email,nombreFamiliar,nombreAmigo);

    }

    private void montarEmail(Session session, String email,String nombreFamiliar
                             ,String nombreAmigo){
        //Iniciamos session.
        Message mensaje = new MimeMessage(session);
        //Punto de partida del emial (emisor)
        try {
            String html = """
        <!DOCTYPE html>
        <html lang="es">
        <body style="
            margin:0;
            padding:30px;
            background:#f5f5f5;
            font-family:Arial, Helvetica, sans-serif;
            color:#333333;
        ">

        <div style="
            max-width:650px;
            margin:auto;
            background:#ffffff;
            border-radius:16px;
            overflow:hidden;
            box-shadow:0 4px 15px rgba(0,0,0,.12);
        ">

            <!-- CABECERA -->
            <div style="
                background:#4F6F52;
                color:white;
                padding:35px 25px;
                text-align:center;
            ">

                <div style="font-size:42px;margin-bottom:10px;">
                    🎄 🎁 🎄
                </div>

                <h1 style="
                    margin:0;
                    font-size:30px;
                    font-weight:bold;
                ">
                    Amigo Invisible
                </h1>
            </div>


            <!-- CONTENIDO -->
            <div style="
                padding:35px 40px;
                text-align:center;
            ">

                <h2 style="
                    color:#4F6F52;
                    margin-top:0;
                ">
                    ¡Hola, %s!
                </h2>

                <p style="
                    font-size:17px;
                    line-height:1.6;
                    color:#555555;
                ">
                    Ha llegado uno de los momentos más esperados.
                    El sorteo del <strong>Amigo Invisible</strong>
                </p>

                <p style="
                    font-size:17px;
                    line-height:1.6;
                    color:#555555;
                ">
                    Este año tendrás que preparar un regalo para...
                </p>


                <!-- PERSONA ASIGNADA -->
                <div style="
                    margin:30px 0;
                    padding:30px 20px;
                    background:#f5f5f5;
                    border:2px dashed #4F6F52;
                    border-radius:12px;
                ">

                    <div style="
                        font-size:38px;
                        margin-bottom:10px;
                    ">
                        🎁
                    </div>

                    <p style="
                        margin:0 0 10px;
                        font-size:14px;
                        color:#777777;
                        text-transform:uppercase;
                        letter-spacing:2px;
                    ">
                        Tu amigo invisible es
                    </p>

                    <h1 style="
                        margin:0;
                        color:#4F6F52;
                        font-size:34px;
                    ">
                        %s
                    </h1>

                </div>


                <!-- MENSAJE -->
                <div style="
                    background:#fafafa;
                    border-left:4px solid #4F6F52;
                    padding:18px 20px;
                    text-align:left;
                    border-radius:4px;
                    margin-top:30px;
                ">

                    <p style="
                        margin:0;
                        line-height:1.6;
                        color:#555555;
                    ">
                        🤫 <strong>Recuerda:</strong> esto es un secreto.
                        No le digas a nadie quién te ha tocado y,
                        sobre todo, ¡que no se entere <strong>%s</strong>!
                    </p>

                </div>
                <p style="
                    font-size:18px;
                    color:#4F6F52;
                    font-weight:bold;
                    margin-bottom:5px;
                ">
                    ¡Mucha suerte!
                </p>

                <p style="
                    margin-top:5px;
                    color:#777777;
                ">
                    Y recuerda... el secreto forma parte del juego.🎉
                </p>

            </div>


            <!-- FOOTER -->
            <div style="
                background:#eeeeee;
                padding:20px;
                text-align:center;
                color:#777777;
                font-size:13px;
            ">
                <p style="margin:0;">
                    © 2026 Alfonso Redondo. Todos los derechos reservados.
                </p>
            </div>

        </div>

        </body>
        </html>
        """.formatted(
                    nombreFamiliar,
                    nombreAmigo,
                    nombreAmigo
            );
        mensaje.setSubject("- AMIGO INVISIBLE - 2027 -");
        //correo que envia
        mensaje.setFrom(new InternetAddress(remitente));
        //correo del familiar que lo va a recibir
        mensaje.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
        //Asunto
        mensaje.setText("\uD83C\uDF81 ¡Ya tienes Amigo Invisible!");
        //Importante: poner el html dentro del correo
        mensaje.setContent(html,"text/html; charset=UTF-8");
        //Enviamos el email
        Transport.send(mensaje);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
