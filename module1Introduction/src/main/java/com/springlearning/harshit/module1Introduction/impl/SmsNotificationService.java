package com.springlearning.harshit.module1Introduction.impl;

import com.springlearning.harshit.module1Introduction.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("smsNotification")
public class SmsNotificationService implements NotificationService {

    @Override
    public void notify(String message) {
        System.out.println("Sms Sending .... " + message);
    }
}
