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

package com.ash.notifications.slack;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.replies.SlackChannelReply;

/**
 * Created by az on 12/05/2016.
 */
public class SendingMessages
{

    /**
     * This method shows how to send a message to a given channel (public channel, private group or direct message channel)
     */
    public void sendMessageToAChannel(SlackSession session, String achannel, String message)
    {

        //get a channel
        SlackChannel channel = session.findChannelByName(achannel);

        session.sendMessage(channel, message);
    }

    /**
     * This method shows how to send a direct message to a user
     */
    public void sendDirectMessageToAUser(SlackSession session)
    {

        //get a user
        SlackUser user = session.findUserByUserName("killroy");

        session.sendMessageToUser(user, "Hi, how are you", null);

    }

    /**
     * This method shows how to send a direct message to a user, but this time it shows how it can be done using the
     * direct message channels
     */
    public void sendDirectMessageToAUserTheHardWay(SlackSession session)
    {

        //get a user
        SlackUser user = session.findUserByUserName("killroy");

        //get its direct message channel
        SlackMessageHandle<SlackChannelReply> reply = session.openDirectMessageChannel(user);

        //get the channel
        SlackChannel channel = reply.getReply().getSlackChannel();

        //send the message to this channel
        session.sendMessage(channel, "Hi, how are you", null);
    }

    /**
     * This method shows how to send a direct message to multiple users.
     */
    public void sendDirectMessageToMultipleUsers(SlackSession session)
    {

        //get some users
        SlackUser killroy = session.findUserByUserName("killroy");
        SlackUser janedoe = session.findUserByUserName("janedoe");
        SlackUser agentsmith = session.findUserByUserName("agentsmith");

        //open a multiparty direct message channel between the bot and these users
        SlackMessageHandle<SlackChannelReply> reply = session.openMultipartyDirectMessageChannel(killroy, janedoe, agentsmith);

        //get the channel
        SlackChannel channel = reply.getReply().getSlackChannel();

        //send the message to this channel
        session.sendMessage(channel, "Hi, how are you guys", null);
    }

}
