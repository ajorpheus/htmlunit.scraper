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

package com.ash.notifications.prowl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import net.sourceforge.prowl.api.DefaultProwlEvent;
import net.sourceforge.prowl.api.ProwlClient;
import net.sourceforge.prowl.api.ProwlEvent;
import net.sourceforge.prowl.exception.ProwlException;

@Component
public class ProwlNotification {

	@Value("${prowl.api.key}")
	private String prowlApiKey;

	@Value("${prowl.notification.launch.url}")
	private String prowlNotificationLaunchUrl;


	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-config.xml");
		ProwlNotification prowlNotification = (ProwlNotification) applicationContext.getBean("prowlNotification");
		prowlNotification.send("DSA Automator", "EARLIER DATE!!", new Date() + "\n" + new Date());
	}

	public void send(String applicationName, String eventName, String messageToBeSent) {
		ProwlClient c = new ProwlClient();
		ProwlEvent e = new DefaultProwlEvent(
				prowlApiKey, applicationName, eventName,
				messageToBeSent, 2, prowlNotificationLaunchUrl);

		String message;
		try {
			message = c.pushEvent(e);
			System.out.println(message);
		} catch (ProwlException e1) {
			e1.printStackTrace();
		}
	}
}
