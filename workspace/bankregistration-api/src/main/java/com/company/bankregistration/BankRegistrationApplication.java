package com.company.bankregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan({ "com.company.bankregistration.*"})
@Import({ TramEventsPublisherConfiguration.class,
		TramMessageProducerJdbcConfiguration.class})
public class BankRegistrationApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankRegistrationApplication.class, args);
	}
}