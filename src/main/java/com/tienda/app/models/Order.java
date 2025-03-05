package com.tienda.app.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "orders")
public class Order {
    /*En la clase Order definimos las entidades de la tabla. También usamos foreign key para asociar el pedido
    con el usuario*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private  User user;

    @Column(nullable = false)
    private BigDecimal total;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime creacionPedido = LocalDateTime.now();

}



/*
Order: Representa un pedido realizado por un usuario. Debe contener:

Un identificador único.
Una referencia al usuario que realizó el pedido.
Un total de la compra.
La fecha de creación del pedido.
 */
