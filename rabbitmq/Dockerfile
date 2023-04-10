FROM rabbitmq:3.9-management-alpine

RUN rabbitmq-plugins enable --offline rabbitmq_management
RUN rabbitmq-plugins enable --offline rabbitmq_prometheus
RUN rabbitmq-plugins enable --offline rabbitmq_consistent_hash_exchange
