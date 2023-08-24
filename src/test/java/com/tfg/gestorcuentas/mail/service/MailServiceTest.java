package com.tfg.gestorcuentas.mail.service;

import com.tfg.gestorcuentas.mail.model.Mail;
import com.tfg.gestorcuentas.utils.Messages;
import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;
    private MailService mailService;
    private Properties javaMailProperties;

    @Before
    public void setUp() throws Exception {
        mailService = new MailService(javaMailSender);
        javaMailProperties = new Properties();
    }

    @Test
    public void enviarMail() throws MessagingException {
        Mail mail = buildMailObject();
        initializeJavaMailProperties();
        Session session = Session.getDefaultInstance(javaMailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("gestorcuentas.adm@gmail.com", "pass");
            }
        });
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo("javiertoyos1@gmail.com");
        mimeMessageHelper.setFrom("gestorcuentas.adm@gmail.com");
        mimeMessageHelper.setSubject("Recuperar contraseña");
        mimeMessageHelper.setText("Este es el contenido");

        given(javaMailSender.createMimeMessage()).willReturn(mimeMessage);

        String resultado = mailService.enviarMail(mail);

        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(mimeMessageHelper.getMimeMessage());

        Assert.assertEquals(Messages.MAIL_ENVIADO_OK.getMessage(), resultado);
    }

    private void initializeJavaMailProperties() {
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.starttls.required", "true");
        javaMailProperties.put("mail.smtp.connectiontimeout", "5000");
        javaMailProperties.put("mail.smtp.timeout", "5000");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.writetimeout", "5000");
    }

    private static Mail buildMailObject() {
        return Mail.builder()
                .withEmisorEmail("gestorcuentas.adm@gmail.com")
                .withAsuntoEmail("Recuperar contraseña")
                .withReceptorEmail("javiertoyos1@gmail.com")
                .withContenidoEmail("Este es el contenido")
                .build();
    }
}