package com.tecsup.app.micro.order.shared.infrastructure.event;


import com.tecsup.app.micro.events.DomainEvent;
import com.tecsup.app.micro.events.OrderConfirmedEvent;
import com.tecsup.app.micro.events.OrderCreatedEvent;


import com.tecsup.app.micro.order.infrastructure.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {

    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;

    public void publish(DomainEvent event) {

        log.info("Publishing {}", event);

        String topic = getTopicFromEvent(event);

        String key = event.getKey();

        //

        this.kafkaTemplate.send(
                topic,
                key,
                event);

    }

    private String getTopicFromEvent(DomainEvent event) {

       if (event instanceof OrderCreatedEvent) {  // AGREGAR
            return KafkaConfig.ORDER_EVENTS_TOPIC;
       }else  if (event instanceof OrderConfirmedEvent) {  // AGREGAR
            return KafkaConfig.ORDER_CONFIRMED_TOPIC;
       } else{
            throw new IllegalArgumentException("Unknown event type: " + event.getEventType());
        }
    }


}
