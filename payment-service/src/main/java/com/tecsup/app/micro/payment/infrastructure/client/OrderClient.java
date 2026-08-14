package com.tecsup.app.micro.payment.infrastructure.client;

import com.tecsup.app.micro.payment.domain.model.Order;
import org.springframework.web.client.HttpClientErrorException;
import com.tecsup.app.micro.payment.infrastructure.client.dto.OrderDto;
import com.tecsup.app.micro.payment.infrastructure.client.mapper.OrderDtoMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderClient {

    private final RestTemplate restTemplate;
    private final OrderDtoMapper orderDtoMapper;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    /**
     * Obtiene una orden por ID desde order-service.
     *
     * @param orderId ID de la orden
     * @param jwtToken JWT para autenticación
     * @return Orden del dominio
     */
    @CircuitBreaker( name = "orderService", fallbackMethod = "getOrderFallback")
    @Retry(name = "orderService")
    public Order getOrderById(   Long orderId,  String jwtToken ) {

        log.info( "Calling Order Service to get order with id: {}",  orderId );
        log.info( "ORDER SERVICE URL CONFIGURADA = [{}]", orderServiceUrl );

        String url =  this.orderServiceUrl + "/api/orders/" + orderId;

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(  MediaType.APPLICATION_JSON );

        // Propagación del JWT
        if (jwtToken != null && !jwtToken.isEmpty()) {
            headers.setBearerAuth(jwtToken);
        } else {
            log.warn("No JWT token provided for Order Service call" );
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<OrderDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, OrderDto.class);
            log.info("Order retrieved successfully from order-service: {}", response.getBody() );

            if (response.getBody() == null) {
                throw new RuntimeException(  "Order Service returned an empty response.");
            }
            return orderDtoMapper.toDomain( response.getBody());

        } catch (HttpClientErrorException.NotFound e) {

            log.warn(
                    "[ORDER CLIENT] Orden NO EXISTE. orderId={}",
                    orderId
            );

            return null;

        } catch (Exception e) {

            log.error(
                    "[ORDER CLIENT] Error consultando order-service. orderId={}",
                    orderId,
                    e
            );

            throw new RuntimeException(
                    "Error calling Order Service",
                    e
            );
        }
    }

    /**
     * Método de compatibilidad sin JWT.
     */
    public Order getOrderById(Long orderId) {
        return getOrderById( orderId,null);
    }

    /**
     * Fallback cuando order-service no está disponible.
     */
    public Order getOrderFallback( Long orderId, String jwtToken, Throwable throwable) {

        log.warn("FALLBACK: Order Service no disponible para orderId: {}. Razón: {}",
                orderId, throwable.getMessage());

        return null;
    }
}