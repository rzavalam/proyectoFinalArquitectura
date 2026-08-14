package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.model.Order;

import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso para obtener todas las órdenes.
 */
@Service
@RequiredArgsConstructor
public class GetAllOrdersUseCase {

    private final OrderRepository orderRepository;


    /**
     * Obtiene todas las órdenes registradas.
     *
     * @return lista de órdenes
     */
    public List<Order> execute() {

        return orderRepository.findAll();
    }
}