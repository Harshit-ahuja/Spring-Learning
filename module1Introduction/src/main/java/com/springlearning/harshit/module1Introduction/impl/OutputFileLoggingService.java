package com.springlearning.harshit.module1Introduction.impl;

import com.springlearning.harshit.module1Introduction.LoggingService;
import org.springframework.stereotype.Component;

@Component
public class OutputFileLoggingService implements LoggingService {

    @Override
    public void print(String log) {
        System.out.println("Printing on output file .... "+ log);
    }
}
