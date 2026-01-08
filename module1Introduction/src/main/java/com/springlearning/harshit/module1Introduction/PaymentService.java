package com.springlearning.harshit.module1Introduction;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {

    public void pay() {
        System.out.println("Paying ....");
    }

    @PostConstruct
    public void init() {
        System.out.println("PaymentService initialised");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Destroying the paymentService bean");
    }
}
