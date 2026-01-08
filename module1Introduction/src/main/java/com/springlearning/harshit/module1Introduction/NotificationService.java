package com.springlearning.harshit.module1Introduction;

import org.springframework.stereotype.Component;

// @Component can be added on interface but serve no real purpose
@Component
public interface NotificationService {

    // Public and abstract by default
    void notify(String message);
}
