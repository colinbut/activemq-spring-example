package com.mycompany.activemq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * App
 */
public class App {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("resources/META-INF/beans.xml");
        MessageSender messageSender = (MessageSender) applicationContext.getBean("messageSender");

        while (true) {
            messageSender.send();
        }
    }
}
