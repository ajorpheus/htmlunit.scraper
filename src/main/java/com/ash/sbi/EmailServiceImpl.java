package com.ash.sbi;

import com.ash.sbi.spec.EmailService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * Created by az on 15/12/2014.
 */
@Component
public class EmailServiceImpl implements EmailService {
    Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    JavaMailSender mailSender;

    @Value("${mail.notification.recipients}")
    private String mailNotificationRecipients;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-config.xml");
        EmailService emailService = (EmailService) applicationContext.getBean("emailServiceImpl");
        emailService.sendEmail("Next Date", "Test subject");
    }

    public void sendEmail(final String body, final String subject) {
        mailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws javax.mail.MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom("important_notification@mail.com");
                message.setTo(StringUtils.split(mailNotificationRecipients, ","));
                message.setSubject(subject);
                message.setText(body, true);
            }
        });
    }

}
