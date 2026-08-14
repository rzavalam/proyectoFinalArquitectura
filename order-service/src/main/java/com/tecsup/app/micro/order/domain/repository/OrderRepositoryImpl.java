package com.tecsup.app.micro.order.domain.repository;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderStatus;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderEntity;
import com.tecsup.app.micro.order.infrastructure.persistence.mapper.OrderPersistenceMapper;
import com.tecsup.app.micro.order.infrastructure.persistence.repository.JpaOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de Orden (Adaptador)
 * Conecta el dominio con la infraestructura de persistencia usando MapStruct.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final OrderPersistenceMapper mapper;

    @Override
    public List<Order> findAll() {
        log.debug("Finding all orders");
        return mapper.toDomainList(jpaOrderRepository.findAll());
    }

    @Override
    public Optional<Order> findById(Long id) {
        log.debug("Finding order by id: {}", id);
        return jpaOrderRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        log.debug("Finding order by number: {}", orderNumber);
        return jpaOrderRepository.findByOrderNumber(orderNumber)
                .map(mapper::toDomain);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        log.debug("Finding orders by userId: {}", userId);
        return mapper.toDomainList(jpaOrderRepository.findByUserId(userId));
    }

    @Override
    public List<Order> findByStatus(String status) {
        log.debug("Finding orders by status: {}", status);
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        return mapper.toDomainList(jpaOrderRepository.findByStatus(orderStatus));
    }

    @Override
    public Order save(Order order) {

        log.debug("Saving order: {}", order.getOrderNumber());

        OrderEntity entity = mapper.toEntity(order);

        // Mantener la relación bidireccional
        if (entity.getItems() != null) {
            entity.getItems().forEach(item -> item.setOrder(entity));
        }

        OrderEntity savedEntity = jpaOrderRepository.save(entity);

        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting order by id: {}", id);
        jpaOrderRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        log.debug("Checking if order exists: {}", id);
        return jpaOrderRepository.existsById(id);
    }

    @Override
    public boolean existsByOrderNumber(String orderNumber) {
        log.debug("Checking if order number exists: {}", orderNumber);
        return jpaOrderRepository.existsByOrderNumber(orderNumber);
    }
}
