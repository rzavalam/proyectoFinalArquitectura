package com.tecsup.app.micro.order.application.saga;

import com.tecsup.app.micro.events.EnrollmentRequestedEvent;
import com.tecsup.app.micro.events.PaymentProcessedEvent;
import com.tecsup.app.micro.order.shared.infrastructure.event.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderSagaHandler {

    private final KafkaEventPublisher kafkaEventPublisher;

    @Transactional
    public String requestEnrollment(String studentId, String studentName, String courseId, BigDecimal amount) {

        // 1. Generar ID
        String enrollmentId = "enrollment-" + UUID.randomUUID();

        // 2. Publicar evento para iniciar saga
        EnrollmentRequestedEvent requestEvent = new EnrollmentRequestedEvent(
                enrollmentId,
                studentId,
                studentName,
                courseId,
                amount,
                LocalDateTime.now()
        );

        // Publicar el comando de inscripción
        kafkaEventPublisher.publish(requestEvent);

        return enrollmentId;

    }

    // NUEVO METODO
    /**
     * PASO 2: Reaccionar a pago exitoso
     */
    /*@KafkaListener(
            topics = KafkaConfig.PAYMENT_PROCESSED_TOPIC,
            groupId = "enrollment-saga-group"
    )*/
    @Transactional
    public void handlePaymentProcessed(PaymentProcessedEvent event) {

        log.info("💳 [SAGA] Pago procesado exitosamente");
        log.info("   Enrollment ID: {}", event.getEnrollmentId());
        log.info("   Transaction ID: {}", event.getTransactionId());

        // TODO --> DEBE CONTINUAR EL PROCESO DE MATRICULA

        log.info("✅ [SAGA] Enrollment confirmada");
        log.info("📨 [SAGA] Evento EnrollmentConfirmed publicado");
    }



}
