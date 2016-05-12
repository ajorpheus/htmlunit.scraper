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

package com.ash.sbi.slack;

import java.io.IOException;
import java.net.Proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

/**
 * This sample code is creating a Slack session and is connecting to slack. To get some more details on how to get a
 * token, please have a look here : https://api.slack.com/bot-users
 */
@Component
public class SlackNotification {
	@Value("${slack.bot.token}")
	private String slackBotToken;

	@Value("${slack.notificaiton.channel}")
	private String slackNotificationChannel;

	@Value("${proxy.enabled}")
	private boolean proxyEnabled;
	@Value("${http-proxy.hostname}")
	private String proxyHostName;
	@Value("${http-proxy.port}")
	private int proxyPort;

	public void send(String messageToBeSent) throws IOException {

		SlackSession session;
		if (proxyEnabled) {
            session = SlackSessionFactory.createWebSocketSlackSession(slackBotToken, Proxy.Type.HTTP, proxyHostName, proxyPort);
		} else {
			session = SlackSessionFactory.createWebSocketSlackSession(slackBotToken);
		}
		session.connect();

		SendingMessages sendingMessages = new SendingMessages();
		sendingMessages.sendMessageToAChannel(session,slackNotificationChannel, messageToBeSent);
		session.disconnect();
	}

}
