package meb.gov.tr.ogretmenkervani.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

    public void sendBulkEmail(List<String> to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Alıcıların e-posta adreslerini diziye dönüştürüp setTo metoduna veriyoruz.
        message.setTo(to.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(body);

        emailSender.send(message);
    }
}

