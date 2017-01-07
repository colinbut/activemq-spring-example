package com.mycompany.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Random;

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
                int i = 0;
                while (true) {
                    if (i % 5 == 0) {
                        String message = "Generated random number: " + new Random().nextInt();
                        messageSender.send(message);
                    } else if (i % 3 == 0) {
                        String message = "Generated random number: " + new Random().nextInt();
                        messageSender.sendText(message);
                    } else {
                        String message = "Generated random number: " + new Random().nextInt();
                        messageSender.send(new ActiveMQQueue("activemq_spring_xml_2"), message);
                    }
                    i++;
                }
            }
        }).start();

    }
}
