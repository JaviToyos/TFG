package com.tfg.gestorcuentas.service.login;

import com.tfg.gestorcuentas.data.entity.PersonaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.mail.model.Mail;
import com.tfg.gestorcuentas.mail.service.IMailService;
import com.tfg.gestorcuentas.service.login.model.GetJWTToken;
import com.tfg.gestorcuentas.service.login.model.LoggedUser;
import com.tfg.gestorcuentas.service.login.model.Login;
import com.tfg.gestorcuentas.service.login.model.RecuperarPass;
import com.tfg.gestorcuentas.utils.Constantes;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import jakarta.mail.MessagingException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
public class LoginService implements ILoginService {

    private final IUsuarioJPARepository iusuarioJPARepository;
    private final IMailService iMailService;
    private final GetJWTToken getJWTToken;

    @Autowired
    public LoginService(IUsuarioJPARepository iusuarioJPARepository, IMailService iMailService, GetJWTToken getJWTToken) {
        this.iusuarioJPARepository = iusuarioJPARepository;
        this.iMailService = iMailService;
        this.getJWTToken = getJWTToken;
    }

    @Override
    public LoggedUser login(Login login) throws IllegalArgumentException {
        if (login.getUsername() == null || login.getPasswd() == null)
            throw new IllegalArgumentException(MessageErrors.LOGIN_EMPTY.getErrorCode());

        Optional<UsuarioEntity> entity = iusuarioJPARepository.findByUsername(login.getUsername());
        UsuarioEntity user = entity.orElseThrow(() -> new NoSuchElementException(MessageErrors.USER_NOT_FOUND.getErrorCode()));

        if (user.getBorrado() == 0 && login.getPasswd().equals(user.getPassword())) {
            String token = getJWTToken.getJWTToken(login.getUsername());
            return LoggedUser.builder().withUser(login.getUsername()).withToken(token).build();
        }
        return LoggedUser.builder().build();
    }

    @Override
    public String recuperarPasswdUsuario(RecuperarPass recuperarPass) {
        if (recuperarPass.getUsername() == null || recuperarPass.getEmail() == null)
            throw new IllegalArgumentException(MessageErrors.PASS_BACKUP_EMPTY.getErrorCode());

        Optional<UsuarioEntity> entity = iusuarioJPARepository.findByUsername(recuperarPass.getUsername());
        UsuarioEntity user = entity.orElseThrow(() -> new NoSuchElementException(MessageErrors.USER_NOT_FOUND.getErrorCode()));
        PersonaEntity persona = user.getPersona();

        if (persona == null) {
            throw new NoSuchElementException(MessageErrors.PERSON_NOT_FOUND.getErrorCode());
        }

        if (!recuperarPass.getEmail().equals(persona.getEmail()) || !recuperarPass.getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException(MessageErrors.PASS_BACKUP_NOT_VALID.getErrorCode());

        final String passwdTemp = generarPasswdAleatoria();
        final String fechaEmail = generateFechaEmail();
        final String asuntoEmail = Constantes.ASUNTO_MAIL;
        final String mensajeEmail = String.format(Constantes.CUERPO_MAIL1, passwdTemp);
        final String contenidoEmail = generateContenidoEmail(fechaEmail, mensajeEmail);

        Mail mail = Mail.builder().withEmisorEmail(Constantes.EMISOR_MAIL).withReceptorEmail(recuperarPass.getEmail())
                .withAsuntoEmail(asuntoEmail).withContenidoEmail(contenidoEmail).withTipoContenido().build();
        try {
            iMailService.enviarMail(mail);
        } catch (MessagingException exc) {
            throw new IllegalStateException(MessageErrors.MAIL_NOT_SENT.getErrorCode() + exc.getMessage());
        }
        user.setPassword(DigestUtils.md5Hex(passwdTemp));
        iusuarioJPARepository.save(user);

        return Messages.PASSWORD_MODIFIED.getMessage();
    }

    private String generarPasswdAleatoria() {
        final int longitudPasswd = 10;
        String passwdTemporal = StringUtils.EMPTY;

        final char[] caract = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'e', 'h', 'i', 'j', 'l', 'k', 'm', 'n', 'o', 'p',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9'};

        final Random rand = new Random();

        for (int i = 0; i < longitudPasswd; i++) {
            passwdTemporal += String.valueOf(caract[rand.nextInt(caract.length)]);
        }
        return passwdTemporal;
    }

    private String generateFechaEmail() {
        return String.format(Constantes.FECHA_MAIL,
                Integer.toString(LocalDate.now().getDayOfMonth()),
                LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("ES")),
                Integer.toString(LocalDate.now().getYear()));
    }

    private String generateContenidoEmail(final String fechaEmail, final String mensajeEmail) {
        return Constantes.TABULACION_FECHA + fechaEmail + Constantes.SALTO_LINEA + Constantes.SALUDO_MAIL
                + Constantes.SALTO_LINEA + mensajeEmail + Constantes.SALTO_LINEA + Constantes.CUERPO_MAIL2
                + Constantes.SALTO_LINEA + Constantes.SALTO_LINEA
                + Constantes.TABULACION_DESPEDIDA + Constantes.DESPEDIDA_MAIL + Constantes.SALTO_LINEA
                + Constantes.TABULACION_FIRMA + Constantes.FIRMA_MAIL;
    }

}
