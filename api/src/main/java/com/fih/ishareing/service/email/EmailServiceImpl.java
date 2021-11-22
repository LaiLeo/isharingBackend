package com.fih.ishareing.service.email;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.fih.ishareing.service.email.model.MailRequestVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean sendEmail(final MailRequestVO request) {
        try {
            // pre test connection
            ((JavaMailSenderImpl) mailSender).testConnection();

            MimeMessage mailMessage = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, false, "utf-8");

            messageHelper.setFrom(request.getFrom());
            messageHelper.setTo(InternetAddress.parse(request.getTo()));
            messageHelper.setSubject(request.getSubject());
            messageHelper.setText(request.getContent(), true);
            mailSender.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            logger.error("Send mail fail, error:{}", ex.getMessage());
        } catch (Exception ex) {
            logger.error("An error occurred while sending mail, error:{}", ex.getMessage());
        }

        return false;
    }

}
