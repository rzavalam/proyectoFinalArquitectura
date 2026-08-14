package com.tecsup.app.micro.payment.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaConfig {

    public static final String PAYMENT_PROCESSED_TOPIC = "payment.events";   // AGREGAR
    //public static final String PAYMENT_FAILED_TOPIC = "payment.failed";  // AGREGAR
    // Set QUEUES/PARTITIONS

    /**
     *  Topic de eventos del curso
     * @return
     */


    @Bean
    public NewTopic paymentProcessedTopic() {
        return TopicBuilder
                .name(PAYMENT_PROCESSED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }


}
