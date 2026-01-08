package com.springlearning.harshit.module1Introduction.impl;

import com.springlearning.harshit.module1Introduction.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// @Primary resolves conflict when multiple beans of NotificationService type are available in applicationContext
//@Primary
@Component
@Qualifier("emailNotification")
@ConditionalOnProperty(name = "notification.type", havingValue = "email") // Creates the bean only when the properties file contains the required value of the mentioned property
public class EmailNotificationService implements NotificationService {

    @Override
    public void notify(String message) {
        System.out.println("Email Sending .... " + message);
    }
}
