package com.tecsup.app.micro.payment.domain.repository;

import com.tecsup.app.micro.payment.domain.model.Payment;
import java.util.Optional;
import java.util.List;


public interface PaymentRepository {

    /**
     * Obtiene todos los pagos.
     */
    List<Payment> findAll();

    /**
     * Busca un pago por ID.
     */
    Optional<Payment> findById(Long id);

    /**
     * Busca un pago por su número.
     */
    Optional<Payment> findByPaymentNumber(String paymentNumber);

    /**
     * Busca el pago asociado a una orden.
     */
    Optional<Payment> findByOrderId(Long orderId);

    /**
     * Obtiene los pagos por estado.
     */
    List<Payment> findByStatus(String status);

    /**
     * Guarda un nuevo pago o actualiza uno existente.
     */
    Payment save(Payment payment);

    /**
     * Elimina un pago por ID.
     */
    void deleteById(Long id);

    /**
     * Verifica si existe un pago con el ID dado.
     */
    boolean existsById(Long id);

    /**
     * Verifica si existe un pago para una orden.
     */
    boolean existsByOrderId(Long orderId);

    /**
     * Verifica si existe un pago con el número dado.
     */
    boolean existsByPaymentNumber(String paymentNumber);
}
