package com.mycompany.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * ActiveMQSpringApplication
 */
public class ActiveMQSpringApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQSpringApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("src/main/resources/META-INF/beans.xml");
        final MessageSender messageSender = (MessageSender) applicationContext.getBean("messageSender");
        final MessageReceiver messageReceiver = (MessageReceiver) applicationContext.getBean("messageReceiver");

        LOGGER.info("Starting sending messages..");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    messageSender.send();
                }
            }
        }).start();

    }
}
