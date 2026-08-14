package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderStatus;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Caso de uso para actualizar el estado de una orden.
 */
@Service
@RequiredArgsConstructor
public class UpdateOrderStatusUseCase {


    private final OrderRepository orderRepository;


    /**
     * Actualiza el estado de una orden.
     *
     * @param id identificador de la orden
     * @param status nuevo estado
     * @param jwtToken token JWT del usuario
     * @return orden actualizada
     */
    public Order execute(Long id,
                         OrderStatus status,
                         String jwtToken) {


        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No existe la orden con id: " + id
                        ));


        validateStatus(status);


        order.setStatus(status);


        return orderRepository.save(order);
    }



    /**
     * Valida estados permitidos.
     */
    private void validateStatus( OrderStatus status) {

        boolean valid = switch (status.toString()) {

            case "PENDING",
                 "CONFIRMED",
                 "SHIPPED",
                 "DELIVERED",
                 "CANCELLED" -> true;

            default -> false;
        };


        if (!valid) {

            throw new IllegalArgumentException(
                    "Estado de orden inválido: " + status
            );
        }
    }
}
