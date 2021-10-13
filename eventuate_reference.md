# Eventuate's Domain Events

## PRODUCER CONFIGURATION

Enable the following repositories in component's **pom.xml** file:

```xml
		<repository>
			<id>jcenter</id>
			<name>jcenter</name>
			<url>https://jcenter.bintray.com</url>
			<releases>
				<enabled>true</enabled>
			</releases>
		</repository>
		<repository>
			<id>eventuate-tram-rc</id>
			<url>https://dl.bintray.com/eventuateio-oss/eventuate-maven-rc</url>
			<releases>
				<enabled>true</enabled>
			</releases>
		</repository>
		<repository>
			<id>eventuate-tram-release</id>
			<url>https://dl.bintray.com/eventuateio-oss/eventuate-maven-release</url>
			<releases>
				<enabled>true</enabled>
			</releases>
		</repository>
```

Enable the following dependencies in component's **pom.xml** file:

```xml
    <dependencies>
        ...
		<dependency>
			<groupId>com.company.commons</groupId>
			<artifactId>domain-events-commons</artifactId>
			<version>1.0.0-SNAPSHOT</version>
		</dependency>

		<dependency>
			<groupId>io.eventuate.tram.core</groupId>
			<artifactId>eventuate-tram-spring-events</artifactId>
			<version>0.24.0.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>io.eventuate.tram.core</groupId>
			<artifactId>eventuate-tram-spring-messaging</artifactId>
			<version>0.24.0.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>io.eventuate.tram.core</groupId>
			<artifactId>eventuate-tram-spring-producer-jdbc</artifactId>
			<version>0.24.0.RELEASE</version>
		</dependency>
        ...
    </dependencies>
```
Update Microservice's Spring Boot Main Application to import the following configurations:

```java
...
@SpringBootApplication
@EnableSwagger2
@ComponentScan({"com.company.bankregistration.*"})
@Import({TramEventsPublisherConfiguration.class, TramMessageProducerJdbcConfiguration.class})
public class BankregistrationApplication 
{
	...
```

Include the following variable on the proper Java Service:

```java
@Service
public class BankRegistrationServiceImpl implements BankRegistrationService {

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(BankRegistrationServiceImpl.class);

    ...
	@Autowired
	private DomainEventPublisher domainEventPublisher;
```

Include the publish method where required:

```java

	@Transactional
	@Override
	public void registerBankClientData(Long customerId, Long clientId, String bpId, String digitalBankId) {
		BankCustomerData bankCustomerData = new BankCustomerData(clientId, bpId, digitalBankId);
		bankCustomerData.setBankEnrolled(true);
		bankCustomerData.setCustomerId(customerId);
		bankCustomerDataRepository.save(bankCustomerData);
		BankCustomerDataCreated bankCustomerDataCreated = new BankCustomerDataCreated(
				bankCustomerData.getBankCustomerDataId(), bankCustomerData.getClientId(), bankCustomerData.getBpId(),
				bankCustomerData.getDigitalBankId(), bankCustomerData.getCustomerId());
		domainEventPublisher.publish(BankCustomerData.class, bankCustomerData.getBankCustomerDataId(),
				Arrays.asList(bankCustomerDataCreated));

		logger.info("BankClientData created successfully");
	}

```

### MESSAGE Database Table

Update application.properties file with the following property, in this example, **bank_registration** is the name of the database schema:
```properties
eventuate.database.schema=bank_registration
```

Create the following table alongside microservice's domain model. This table is where the event will be stored:

```sql
CREATE TABLE `message` (
  `id` VarChar(255) PRIMARY KEY,
  `destination` VarChar(1000) NOT NULL,
  `headers` VarChar(1000) NOT NULL,
  `payload` VarChar(3000) NOT NULL,
  `creation_time` BIGINT,
  `published` VarChar(1) default '0'
);
```


## CONSUMER CONFIGURATION

Enable the following repositories in component's **pom.xml** file:

```xml
	<repositories>
		<repository>
			<id>jcenter</id>
			<name>jcenter</name>
			<url>https://jcenter.bintray.com</url>
			<releases>
				<enabled>true</enabled>
			</releases>
		</repository>
	</repositories>
```

Enable the following dependencies in component's **pom.xml** file:

```xml
    ...
    <dependencies>
        ...
		<dependency>
			<groupId>com.company.commons</groupId>
			<artifactId>domain-events-commons</artifactId>
			<version>1.0.0-SNAPSHOT</version>
		</dependency>
		<dependency>
			<groupId>io.eventuate.tram.core</groupId>
			<artifactId>eventuate-tram-spring-events</artifactId>
			<version>0.24.0.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>io.eventuate.tram.core</groupId>
			<artifactId>eventuate-tram-consumer-rabbitmq</artifactId>
			<version>0.24.0.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>io.eventuate.tram.core</groupId>
			<artifactId>eventuate-tram-consumer-common</artifactId>
			<version>0.24.0.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>io.eventuate.tram.core</groupId>
			<artifactId>eventuate-tram-consumer-jdbc</artifactId>
			<version>0.24.0.RELEASE</version>
		</dependency>        
		<dependency>
			<groupId>io.eventuate.tram.core</groupId>
			<artifactId>eventuate-tram-spring-consumer-jdbc</artifactId>
			<version>0.24.0.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>io.eventuate.tram.core</groupId>
			<artifactId>eventuate-tram-spring-producer-jdbc</artifactId>
			<version>0.24.0.RELEASE</version>
		</dependency>
        ...
    </dependencies>
```

Create a new class to define DomainEventHandlers, registering all the events that the component is interested in:

```java
public class BankCustomerDataHandler {

	Logger logger = LoggerFactory.getLogger(BankCustomerDataHandler.class);

	private BankCustomerDataRepository bankCustomerDataRepository;

	public BankCustomerDataHandler(BankCustomerDataRepository bankCustomerDataRepository) {
		this.bankCustomerDataRepository = bankCustomerDataRepository;
	}

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder.forAggregateType("com.company.bankregistration.service.model.entities.BankCustomerData")
				.onEvent(BankCustomerDataCreated.class, this::handleBankCustomerDataCreated)
				.build();
	}

	public void handleBankCustomerDataCreated(DomainEventEnvelope<BankCustomerDataCreated> bankCustomerDataCreated) {
		logger.info("Handle BankCustomerDataCreated Event.");
		if (bankCustomerDataCreated != null && bankCustomerDataCreated.getEvent() != null) {
			BankCustomerData bankCustomerData = null;
			Long customerId = bankCustomerDataCreated.getEvent().getCustomerId();
			String bpId = bankCustomerDataCreated.getEvent().getBpId();
			Long clientId = bankCustomerDataCreated.getEvent().getClientId();

			Optional<BankCustomerData> bankCustomerDataExists = this.bankCustomerDataRepository.findById(bankCustomerDataCreated.getEvent().getBankCustomerDataId());
			if (bankCustomerDataExists.isPresent()) {
				bankCustomerData = bankCustomerDataExists.get();
				logger.warn("The Customer [{}] already exists", bankCustomerData.getCustomerId());
			} else {
				bankCustomerData = this.bankCustomerDataRepository.save(new BankCustomerData(bankCustomerDataCreated.getEvent().getBankCustomerDataId(),
						clientId, customerId, bpId));
				logger.info("The Customer [{}] was created", bankCustomerData.getCustomerId());
				logger.debug("Saved [{}]", bankCustomerData.toString());
			}
		} else
			logger.error("Receiving a null bankCustomerDataCreated");
	}

}
```

Create a configuration class to configure Eventuate and Domain Handlers:

```java
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

```

Import the Configuration class from Spring Boot's main application class:

```java
...
@SpringBootApplication
@EnableSwagger2
@ComponentScan({"com.company.*"})
@Import({DomainEventsConsumerConfiguration.class})
public class OathApplication 
{
	...
```

Add the following propierties to spring boot's application properties file:

```properties
eventuatelocal.zookeeper.connection.string=localhost:32771
rabbitmq.url=localhost
```

### RECEIVED_MESSAGES Database Table

Update application.properties file with the following property, in this example, **oath** is the name of the database schema:
```properties
eventuate.database.schema=oath
```


For the **SqlTableBasedDuplicateMessageDetector** to work, create the following table alongside microservice's domain model:

```sql
CREATE TABLE `received_messages` (
  `consumer_id` VarChar(255),
  `message_id` VarChar(255),
  `creation_time` BIGINT,
  PRIMARY KEY(`consumer_id`, `message_id`)
);
```
