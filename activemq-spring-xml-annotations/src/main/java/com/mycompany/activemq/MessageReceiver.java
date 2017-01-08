/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.activemq;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Service
public class MessageReceiver implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
        try {
            LOGGER.info(activeMQTextMessage.getText());
        } catch (JMSException e) {
            LOGGER.error("{}", e);
        }
    }
}
