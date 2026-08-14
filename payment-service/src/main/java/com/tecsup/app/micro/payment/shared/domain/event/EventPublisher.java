package com.tecsup.app.micro.payment.shared.domain.event;

import com.tecsup.app.micro.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventPublisher {

    private final ApplicationEventPublisher publisher;

    /**
     *  Método que publica el evento
     * @param event
     */
    public void publish(DomainEvent event) {
        log.info("Publishing event: {}", event);
        this.publisher.publishEvent(event);
    }


}
