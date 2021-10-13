package com.company.domain.events.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.company.oath.service.repository.BankCustomerDataRepository;

import io.eventuate.common.jdbc.EventuateJdbcStatementExecutor;
import io.eventuate.common.jdbc.EventuateSchema;
import io.eventuate.common.jdbc.EventuateTransactionTemplate;
import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.consumer.jdbc.SqlTableBasedDuplicateMessageDetector;
import io.eventuate.tram.consumer.rabbitmq.EventuateTramRabbitMQMessageConsumerConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.spring.events.subscriber.TramEventSubscriberConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;

@Configuration
@Import({EventuateTramRabbitMQMessageConsumerConfiguration.class, TramEventSubscriberConfiguration.class, TramMessageProducerJdbcConfiguration.class})
public class DomainEventsConsumerConfiguration {
	
	@Value("${eventuate.eventDispatcherId}")
	private String eventDispatcherId;

	@Autowired
	EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor;

	@Autowired
	EventuateTransactionTemplate eventuateTransactionTemplate;

	@Bean
	public BankCustomerDataHandler BankRegistrationHandler(BankCustomerDataRepository bankCustomerDataRepository) {
		return new BankCustomerDataHandler(bankCustomerDataRepository);
	}

	@Bean
	public DomainEventDispatcher bankRegistrationDomainEventDispatcher(BankCustomerDataHandler BankRegistrationHandler,
			DomainEventDispatcherFactory domainEventDispatcherFactory) {
		return domainEventDispatcherFactory.make(this.eventDispatcherId, BankRegistrationHandler.domainEventHandlers());
	}

	@Bean
	public DuplicateMessageDetector duplicateMessageDetector(EventuateSchema eventuateSchema) {
		return new SqlTableBasedDuplicateMessageDetector(eventuateSchema, "ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)",
				eventuateJdbcStatementExecutor, eventuateTransactionTemplate);
	}
}
