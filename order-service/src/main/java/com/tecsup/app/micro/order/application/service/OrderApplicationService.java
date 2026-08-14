package com.tecsup.app.micro.order.application.service;

import com.tecsup.app.micro.order.application.usecase.*;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de aplicación para la gestión de órdenes.
 *
 * Orquesta los casos de uso sin contener lógica de negocio.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderApplicationService {

    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final GetOrdersByUserUseCase getOrdersByUserUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    /**
     * Obtiene todas las órdenes.
     */
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {

        return getAllOrdersUseCase.execute();
    }

    /**
     * Obtiene una orden por id.
     */
    @Transactional(readOnly = true)
    public Order getOrderById(Long id, String jwtToken) {

        return getOrderByIdUseCase.execute(id, jwtToken);
    }

    /**
     * Obtiene todas las órdenes de un usuario.
     */
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userId, String jwtToken) {

        return getOrdersByUserUseCase.execute(userId, jwtToken);
    }

    /**
     * Registra una nueva orden.
     *
     * El caso de uso:
     * - valida usuario
     * - valida productos
     * - obtiene precios actuales
     * - calcula subtotales
     * - calcula total
     * - guarda la orden
     */
    @Transactional
    public Order createOrder(Order order, String jwtToken) {

        return createOrderUseCase.execute(order, jwtToken);
    }

    /**
     * Actualiza el estado de la orden.
     */
    @Transactional
    public Order updateOrderStatus(Long id,
                                   OrderStatus status,
                                   String jwtToken) {

        return updateOrderStatusUseCase.execute(id, status, jwtToken);
    }

    /**
     * Elimina una orden.
     */
    @Transactional
    public void deleteOrder(Long id) {

        deleteOrderUseCase.execute(id);
    }

    @Transactional
    public Order confirmOrder(Long orderId) {

        return confirmOrderUseCase.execute(orderId);
    }

    @Transactional
    public Order cancelOrder(Long orderId) {

        return cancelOrderUseCase.execute(orderId);
    }

}