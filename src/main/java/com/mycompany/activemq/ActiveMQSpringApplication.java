package com.mycompany.activemq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * ActiveMQSpringApplication
 */
public class ActiveMQSpringApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("src/main/resources/META-INF/beans.xml");
        MessageSender messageSender = (MessageSender) applicationContext.getBean("messageSender");

        while (true) {
            messageSender.send();
        }
    }
}
