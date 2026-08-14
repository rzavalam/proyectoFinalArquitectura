package com.tecsup.app.micro.order.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaConfig {

    // Set TOPICS
    public static final String ORDER_EVENTS_TOPIC = "order.events";
    public static final String PAYMENT_PROCESSED_TOPIC = "payment.events";   // AGREGAR
    public static final String DELIVERY_EVENTS_TOPIC = "delivery.events";
    public static final String ORDER_CONFIRMED_TOPIC = "order.confirmed";

    /**
     *  Topic de eventos del curso
     * @return
     */
    @Bean
    public NewTopic orderEventTopic() {

        return new NewTopic(ORDER_EVENTS_TOPIC,  // topic
                3,   // Nro. particiones
                (short) 1  // Nro. de replicas
        );
    }

    /**
     *  Topic de eventos del curso
     * @return
     */
    @Bean
    public NewTopic orderConfirmadoEventTopic() {

        return new NewTopic(ORDER_CONFIRMED_TOPIC,  // topic
                3,   // Nro. particiones
                (short) 1  // Nro. de replicas
        );
    }
    @Bean
    public NewTopic paymentEventTopic() {
        return TopicBuilder
                .name(PAYMENT_PROCESSED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }


    // NUEVO BEAN
    @Bean
    public NewTopic deliveryEventTopic() {
        return TopicBuilder
                .name(DELIVERY_EVENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

}
