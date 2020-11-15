package com.pluralsight.conference.util;

import com.pluralsight.conference.model.Password;
import com.pluralsight.conference.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PasswordListener implements ApplicationListener<OnPasswordResetEvent> {

    private String serverUrl = "http://localhost:8080/";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordService passwordService;

    @Override
    public void onApplicationEvent(OnPasswordResetEvent onPasswordResetEvent) {
        this.resetPassword(onPasswordResetEvent);
    }

    private void resetPassword(OnPasswordResetEvent onPasswordResetEvent) {
        Password password = onPasswordResetEvent.getPassword();
        String token = UUID.randomUUID().toString();
        passwordService.createResetToken(password, token);

        String recipientAddress = password.getEmail();
        String subject = "Reset Password";
        String confirmationUrl = onPasswordResetEvent.getAppUrl() + "/passwordReset?token=" + token;
        String message = "Reset password:";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + serverUrl + confirmationUrl);
        mailSender.send(email);
    }
}
