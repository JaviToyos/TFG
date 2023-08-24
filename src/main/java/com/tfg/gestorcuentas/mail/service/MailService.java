package com.tfg.gestorcuentas.mail.service;

import com.tfg.gestorcuentas.mail.model.Mail;
import com.tfg.gestorcuentas.utils.Messages;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String enviarMail(Mail mail) throws MessagingException {
        String resultado = StringUtils.EMPTY;
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(mail.getReceptorEmail());
        mimeMessageHelper.setFrom(mail.getEmisorEmail());
        mimeMessageHelper.setSubject(mail.getAsuntoEmail());
        mimeMessageHelper.setText(mail.getContenidoEmail());

        javaMailSender.send(mimeMessageHelper.getMimeMessage());

        resultado = Messages.MAIL_ENVIADO_OK.getMessage();

        return resultado;

    }
}
