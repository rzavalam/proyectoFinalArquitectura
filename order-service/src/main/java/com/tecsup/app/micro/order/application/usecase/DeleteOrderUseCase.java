package com.tecsup.app.micro.order.application.usecase;


import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Caso de uso para eliminar órdenes.
 */
@Service
@RequiredArgsConstructor
public class DeleteOrderUseCase {


    private final OrderRepository orderRepository;



    /**
     * Elimina una orden por id.
     *
     * @param id identificador de orden
     */
    public void execute(Long id) {


        boolean exists = orderRepository.existsById(id);


        if (!exists) {

            throw new RuntimeException(
                    "No existe la orden con id: " + id
            );
        }


        orderRepository.deleteById(id);
    }
}
