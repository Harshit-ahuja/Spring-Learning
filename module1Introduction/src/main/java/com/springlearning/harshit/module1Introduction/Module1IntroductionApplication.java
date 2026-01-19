package com.springlearning.harshit.module1Introduction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class Module1IntroductionApplication implements CommandLineRunner {

	final Optional<NotificationService> notificationService;

	@Autowired
	PaymentService paymentService1;  // Field Injection (Not a recommended way of DI)

	@Autowired
	PaymentService paymentService2; // Field Injection

	@Autowired(required = false) // This initialises object with null if bean not present in applicationContext. This is called an optional bean
	ExtraService extraService;

	/* 1. Constructor Injection - Preferred over field injection for DI because it helps in making the
	notificationService field 'final' which makes it immutable.
	2. Here, EmailNotificationService injected as that is annotated with primary.
    3. @Autowired is not necessary here (but doesn't create issue if added).
    4. Also Here, Optional<NotificationService> makes it an optional bean. That means if bean isn't present in applicationContext,
    null would be assigned.
    5. @Nullable NotificationService would have done the same job here as Optional. */
	public Module1IntroductionApplication(@Qualifier("smsNotification") Optional<NotificationService> notificationService) {
		this.notificationService = notificationService;
	}

	// DI of all available beans of LoggingServiceType
	@Autowired
	Map<String, LoggingService> loggingServiceMap = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(Module1IntroductionApplication.class, args);
	}

	/* Once all beans are created by spring and DI is completed, spring runs all the CommandLineRunner
    in the application. CommandLineRunner interface provides a run method which has all the command line arguments
    passed and the method is non-static unlike main and non-static objects can be accessed here
	*/
	@Override
	public void run(String... args) throws Exception {
		paymentService1.pay();

		// Different hashCode because of prototype scope
		System.out.println(paymentService1.hashCode());
		System.out.println(paymentService2.hashCode());

		if(notificationService.isPresent()) {
			notificationService.get().notify("hello");
		}

		// var is a local variable introduced in java10. Compiler decides type at compile time
		for(var loggingService : loggingServiceMap.entrySet()) {
			System.out.println(loggingService.getKey());
			loggingService.getValue().print("log");
		}
		
		System.out.println(extraService.hashCode());
	}
}
