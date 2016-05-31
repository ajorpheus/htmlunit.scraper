/*
 *
 *  * Copyright 2003-2015 Monitise Group Limited. All Rights Reserved.
 *  *
 *  * Save to the extent permitted by law, you may not use, copy, modify,
 *  * distribute or create derivative works of this material or any part
 *  * of it without the prior written consent of Monitise Group Limited.
 *  * Any reproduction of this material must contain this notice.
 *
 */

package com.ash.notifications.email;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

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
        emailService.sendEmail("Test subject", "Next Date");
    }

    public void sendEmail(final String subject, final String body) {
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
