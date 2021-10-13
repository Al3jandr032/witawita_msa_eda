package com.company.oath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.company.domain.events.consumer.DomainEventsConsumerConfiguration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author bankaya
 * 
 */
@SpringBootApplication
@EnableSwagger2
@Import(DomainEventsConsumerConfiguration.class)
@ComponentScan({"com.company.oath.*"})
public class OathApplication {
	public static void main( String[] args )
	{
		SpringApplication.run(OathApplication.class, args);
	}
}