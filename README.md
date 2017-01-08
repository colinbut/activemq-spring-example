# ActiveMQ - Spring Example

This project repository demonstrates ways to use ActiveMQ with the Spring framework.

Spring provides JMS abstractions for working with messaging. All the low level JMS implementations by ActiveMQ is hidden away by Spring.

ActiveMQ implements JMS.

Just use `JmsTemplate`

### Spring XML Configuration

This sub project shows how to simply integrate ActiveMQ with Spring. Uses the traditional XML configuration approach.

need to define an application context file:

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="amqConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
        <constructor-arg index="0" value="tcp://localhost:61616"/>
    </bean>

    <bean id="connectionFactory" class="org.springframework.jms.connection.CachingConnectionFactory">
        <constructor-arg ref="amqConnectionFactory"/>
    </bean>

    <bean id="queueDestination" class="org.apache.activemq.command.ActiveMQQueue">
        <constructor-arg index="0" value="queue_name"/>
    </bean>

    <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
        <property name="connectionFactory" ref="connectionFactory"/>
        <property name="defaultDestination" ref="queueDestination"/>
    </bean>

    <!-- my bean that sends jms messages to activemq -->
    <bean id="messageSender" class="com.mycompany.activemq.MessageSender">
        <constructor-arg index="0" ref="jmsTemplate"/>
    </bean>

    <bean id="messageReceiver" class="com.mycompany.activemq.MessageReceiver"/>

    <bean class="org.springframework.jms.listener.SimpleMessageListenerContainer">
        <property name="connectionFactory" ref="connectionFactory"/>
        <property name="destinationName" value="activemq_spring_xml"/>
        <property name="messageListener" ref="messageReceiver"/>
    </bean>

</beans>
```

need to start up the ActiveMQ broker instance.

### Spring XML Configuration with Annotations (component scanning)

also need to define a application context file.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- component scanning for beans -->
    <context:component-scan base-package="com.mycompany.activemq"/>

    <bean id="amqConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
        <constructor-arg index="0" value="tcp://localhost:61616"/>
    </bean>

    <bean id="connectionFactory" class="org.springframework.jms.connection.CachingConnectionFactory">
        <constructor-arg ref="amqConnectionFactory"/>
    </bean>

    <bean id="queueDestination" class="org.apache.activemq.command.ActiveMQQueue">
        <constructor-arg index="0" value="activemq_spring_xml_annotations"/>
    </bean>

    <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
        <property name="connectionFactory" ref="connectionFactory"/>
        <property name="defaultDestination" ref="queueDestination"/>
    </bean>


    <bean class="org.springframework.jms.listener.SimpleMessageListenerContainer">
        <property name="connectionFactory" ref="connectionFactory"/>
        <property name="destinationName" value="activemq_spring_xml_annotations"/>
        <property name="messageListener" ref="messageReceiver"/>
    </bean>


</beans>
```

And simply annotate the beans:

```
@Service
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;
```

need to start up the ActiveMQ broker instance.

### Spring Boot - Spring JMS - Java Configuration

This sub project shows Bootstrapping spring application that uses ActiveMQ with Spring Boot.

Spring Boot starts up an embedded ActiveMQ broker instance.

No XML configuration required.

Just using Spring Java Configuration. All configuration is done using Java code. Very simple and clean.

```
@SpringBootApplication
@EnableJms
public class Application {

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer defaultJmsListenerContainerFactoryConfigurer) {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactoryConfigurer.configure(defaultJmsListenerContainerFactory, connectionFactory);
        return defaultJmsListenerContainerFactory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        //....
    }

```

The consumer:

```
@Component
public class Receiver {

    @JmsListener(destination = "destination", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        //....
    }
}
```

For Prodcuer:

just simply get the `JmsTemplate` and use one of its send methods
