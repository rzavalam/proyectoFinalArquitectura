    -- ============================================
    -- Migration: V1__CREATE_TABLES.sql
    -- Database: paymentdb (Docker container: postgres-payment)
    -- ============================================

    CREATE TABLE deliveries (
                                id BIGSERIAL PRIMARY KEY,
                                order_id BIGINT NOT NULL,
                                user_id BIGINT NOT NULL,
                                delivery_address VARCHAR(255) NOT NULL,
                                delivery_person_name VARCHAR(150),
                                delivery_person_phone VARCHAR(20),

                                status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

                                estimated_delivery_at TIMESTAMP,

                                picked_up_at TIMESTAMP,

                                delivered_at TIMESTAMP,

                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                CONSTRAINT chk_delivery_status
                                    CHECK (
                                        status IN (
                                                   'PENDING',
                                                   'ASSIGNED',
                                                   'PICKED_UP',
                                                   'IN_TRANSIT',
                                                   'DELIVERED',
                                                   'FAILED',
                                                   'CANCELLED'
                                            )
                                        )
    );