package com.tfg.gestorcuentas.mail.service;

import com.tfg.gestorcuentas.mail.model.Mail;
import jakarta.mail.MessagingException;

public interface IMailService {
    String enviarMail(Mail mail) throws MessagingException;
}
