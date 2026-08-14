# Documento de Arquitectura de Software -- Sistema de Pedidos de Comida

**Arquitectura de Software · V1.1**

## 1. Introducción General

### 1.1 Propósito del documento

Documentar la arquitectura de un sistema de pedidos de comida basado en
microservicios, describiendo sus principales componentes,
responsabilidades, comunicación entre servicios, persistencia de datos y
flujo general del sistema.

### 1.2 Alcance del sistema

El sistema permite a los usuarios:

-   Registrarse e iniciar sesión.
-   Consultar restaurantes y productos disponibles.
-   Crear pedidos.
-   Validar productos y disponibilidad.
-   Procesar pagos.
-   Coordinar la entrega de los pedidos.
-   Consultar el estado de sus pedidos.

El sistema está compuesto por cinco microservicios independientes:

-   `user-service`
-   `product-service`
-   `order-service`
-   `payment-service`
-   `delivery-service`

------------------------------------------------------------------------

# 2. Visión Arquitectónica General

## 2.1 Estilo arquitectónico utilizado

El sistema utiliza una **Arquitectura de Microservicios**, donde cada
funcionalidad principal se encuentra implementada como un servicio
independiente.

Los microservicios son desarrollados con Spring Boot y desplegados
mediante contenedores Docker y Kubernetes.

Cada servicio mantiene su propia responsabilidad y persistencia,
evitando el acoplamiento directo entre las bases de datos de los
diferentes servicios.

### Microservicios

  --------------------------------------------------------------------------
  Microservicio         Responsabilidad                               Puerto
  --------------------- ----------------------- ----------------------------
  `user-service`        Usuarios, autenticación                         8081
                        y generación/validación 
                        de JWT                  

  `product-service`     Restaurantes,                                   8082
                        productos, precios y    
                        stock                   

  `order-service`       Creación y gestión de                           8083
                        pedidos                 

  `payment-service`     Procesamiento y gestión                         8084
                        de pagos                

  `delivery-service`    Gestión y coordinación                          8085
                        de entregas             
  --------------------------------------------------------------------------

> Los puertos indicados corresponden a la exposición local de los
> servicios. En Kubernetes, la comunicación entre microservicios se
> realiza mediante Services internos del clúster.

------------------------------------------------------------------------

## 2.2 Arquitectura de Microservicios

``` text
                         ┌──────────────────────┐
                         │       CLIENTE        │
                         │   Postman / Frontend │
                         └──────────┬───────────┘
                                    │
                                    │ HTTP / REST
                                    ▼
                    ┌──────────────────────────────┐
                    │        USER-SERVICE          │
                    │          :8081               │
                    │                              │
                    │ - Registro de usuarios       │
                    │ - Login                      │
                    │ - JWT                        │
                    └──────────────┬───────────────┘
                                   │
                                   │ JWT
                                   ▼
                    ┌──────────────────────────────┐
                    │       PRODUCT-SERVICE        │
                    │          :8082               │
                    │                              │
                    │ - Restaurantes               │
                    │ - Productos                  │
                    │ - Precios                    │
                    │ - Disponibilidad / Stock     │
                    └──────────────┬───────────────┘
                                   │
                                   │ HTTP / REST
                                   ▼
                    ┌──────────────────────────────┐
                    │        ORDER-SERVICE         │
                    │          :8083               │
                    │                              │
                    │ - Crear pedidos              │
                    │ - Validar usuario            │
                    │ - Validar productos          │
                    │ - Calcular total             │
                    │ - Gestionar estado del pedido│
                    └──────────────┬───────────────┘
                                   │
                    ┌──────────────┴───────────────┐
                    │                              │
                    │ HTTP / REST                  │ HTTP / REST
                    ▼                              ▼
       ┌─────────────────────────┐    ┌─────────────────────────┐
       │    PAYMENT-SERVICE      │    │    DELIVERY-SERVICE     │
       │         :8084           │    │         :8085           │
       │                         │    │                         │
       │ - Procesar pagos        │    │ - Gestionar entregas    │
       │ - Validar pago          │    │ - Asignar repartidor    │
       │ - Estado del pago       │    │ - Estado de entrega     │
       └─────────────────────────┘    └─────────────────────────┘
```

### Comunicación entre microservicios

La comunicación entre los microservicios se realiza mediante
**HTTP/REST**.

El `order-service` funciona como componente central del flujo de
creación del pedido y se comunica con:

-   `user-service` para validar u obtener información del usuario.
-   `product-service` para consultar productos, precios y
    disponibilidad.
-   `payment-service` para procesar el pago.
-   `delivery-service` para solicitar y coordinar la entrega.

El cliente obtiene un **JWT** mediante `user-service` y posteriormente
utiliza dicho token para acceder a los recursos protegidos de los demás
microservicios.

------------------------------------------------------------------------

## 2.3 Contenedores Docker

Cada microservicio cuenta con su propia imagen Docker.

Las imágenes se construyen mediante:

``` bash
docker build -t user-service:1.0 .
docker build -t product-service:1.0 .
docker build -t order-service:1.0 .
docker build -t payment-service:1.0 .
docker build -t delivery-service:1.0 .
```

Estas imágenes pueden ser utilizadas posteriormente para ejecutar los
microservicios de forma independiente o desplegarlos en Kubernetes.

------------------------------------------------------------------------

# 3. Flujo General del Sistema

El flujo principal para realizar un pedido es:

1.  El usuario inicia sesión en `user-service`.
2.  `user-service` valida las credenciales.
3.  `user-service` genera un token JWT.
4.  El cliente consulta los productos disponibles mediante
    `product-service`.
5.  El cliente crea un pedido mediante `order-service`.
6.  `order-service` valida la información del usuario.
7.  `order-service` consulta `product-service` para verificar productos,
    precios y disponibilidad.
8.  `order-service` crea el pedido y calcula el total.
9.  `order-service` solicita el procesamiento del pago a
    `payment-service`.
10. `payment-service` procesa y confirma el pago.
11. `order-service` solicita la entrega a `delivery-service`.
12. `delivery-service` registra y programa la entrega.
13. El pedido queda asociado a su información de pago y entrega.

------------------------------------------------------------------------

# 4. Diagrama de Secuencia

El siguiente diagrama representa el flujo principal desde el inicio de
sesión hasta la coordinación de la entrega.

El diagrama de secuencia se encuentra en la carpeta `imagenes` del
proyecto y se referencia mediante una ruta relativa para que pueda ser
visualizado correctamente desde GitHub.

![Diagrama de Secuencia](./imagenes/diagrama-secuencia.png)

> **Ubicación local de la imagen:**  
> `D:\Proyectos\arquitectura\kubernate-proyectofinal\arq_m4_s2_k8s_micro\imagenes\diagrama-secuencia.png`


------------------------------------------------------------------------

# 5. Responsabilidades de los Microservicios

## 5.1 User Service

El `user-service` es responsable de la gestión de usuarios y
autenticación.

Principales responsabilidades:

-   Registro de usuarios.
-   Consulta de usuarios.
-   Inicio de sesión.
-   Validación de credenciales.
-   Generación de tokens JWT.
-   Validación de información asociada al usuario.

------------------------------------------------------------------------

## 5.2 Product Service

El `product-service` administra la información relacionada con
restaurantes y productos.

Principales responsabilidades:

-   Registrar restaurantes.
-   Consultar restaurantes.
-   Registrar productos.
-   Consultar productos.
-   Gestionar precios.
-   Gestionar disponibilidad.
-   Validar stock de productos.

------------------------------------------------------------------------

## 5.3 Order Service

El `order-service` administra el ciclo de vida de los pedidos.

Principales responsabilidades:

-   Crear pedidos.
-   Asociar pedidos con usuarios.
-   Registrar productos del pedido.
-   Validar productos.
-   Obtener precios actuales.
-   Calcular el total del pedido.
-   Gestionar estados del pedido.
-   Coordinar el procesamiento del pago.
-   Coordinar la entrega.

------------------------------------------------------------------------

## 5.4 Payment Service

El `payment-service` administra el procesamiento de pagos asociados a
los pedidos.

Principales responsabilidades:

-   Registrar pagos.
-   Procesar pagos.
-   Asociar el pago con un pedido.
-   Registrar el monto pagado.
-   Gestionar el estado del pago.
-   Informar si el pago fue aprobado o rechazado.

------------------------------------------------------------------------

## 5.5 Delivery Service

El `delivery-service` administra la entrega de los pedidos.

Principales responsabilidades:

-   Registrar solicitudes de entrega.
-   Asociar una entrega con un pedido.
-   Registrar dirección de entrega.
-   Asignar repartidor.
-   Programar la entrega.
-   Gestionar el estado de la entrega.

------------------------------------------------------------------------

# 6. Modelo de Datos

Cada microservicio mantiene su propia persistencia, siguiendo el
principio de **Database per Service**.

``` text
┌───────────────────────┐
│     USER-SERVICE      │
│                       │
│       user-db         │
│                       │
│  Usuarios             │
└───────────────────────┘


┌───────────────────────┐
│    PRODUCT-SERVICE    │
│                       │
│      product-db       │
│                       │
│  Restaurantes         │
│  Productos            │
└───────────────────────┘


┌───────────────────────┐
│     ORDER-SERVICE     │
│                       │
│       order-db        │
│                       │
│  Orders               │
│  Order Items          │
└───────────────────────┘


┌───────────────────────┐
│    PAYMENT-SERVICE    │
│                       │
│      payment-db       │
│                       │
│  Payments             │
└───────────────────────┘


┌───────────────────────┐
│    DELIVERY-SERVICE   │
│                       │
│      delivery-db      │
│                       │
│  Deliveries           │
└───────────────────────┘
```

------------------------------------------------------------------------

# 7. Seguridad y Resilencia

El sistema utiliza **JWT (JSON Web Token)** y **CircuitBreaker**  para proteger los endpoints
que requieren autenticación.

El flujo general es:

``` text
Cliente
   │
   │ email + password
   ▼
user-service
   │
   │ Validación
   ▼
JWT
   │
   │ Authorization: Bearer <token>
   ▼
Microservicios protegidos
```

El token JWT permite identificar al usuario y controlar el acceso a los
recursos protegidos.

------------------------------------------------------------------------

# 8. Despliegue

Los microservicios son empaquetados como imágenes Docker independientes:

``` text
user-service:1.0
product-service:1.0
order-service:1.0
payment-service:1.0
delivery-service:1.0
```

Posteriormente pueden ser desplegados en Kubernetes mediante
`Deployment` y `Service` independientes para cada microservicio.

``` text
                    Kubernetes
                         │
       ┌─────────────────┼──────────────────┐
       │                 │                  │
       ▼                 ▼                  ▼
 user-service      product-service     order-service
       │                 │                  │
       │                 │          ┌───────┴────────┐
       │                 │          ▼                ▼
       │                 │   payment-service   delivery-service
       │                 │
       ▼                 ▼
    user-db          product-db  

                         order-db
                         payment-db
                         delivery-db
```

------------------------------------------------------------------------

# 9. Resumen de la Arquitectura

La arquitectura final está compuesta por **cinco microservicios
independientes**:

``` text
┌─────────────────┐
│  USER-SERVICE   │
│ Autenticación   │
│ Usuarios + JWT  │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ PRODUCT-SERVICE │
│ Restaurantes    │
│ Productos/Stock │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│  ORDER-SERVICE  │
│ Pedidos         │
│ Total           │
│ Orquestación    │
└───────┬─┬───────┘
        │ │
        │ └──────────────────┐
        ▼                    ▼
┌─────────────────┐  ┌──────────────────┐
│ PAYMENT-SERVICE │  │ DELIVERY-SERVICE │
│ Pagos           │  │ Entregas         │
└─────────────────┘  └──────────────────┘
```

La solución permite separar responsabilidades, escalar los servicios de
manera independiente, aislar las bases de datos y facilitar el
despliegue mediante Docker y Kubernetes.
