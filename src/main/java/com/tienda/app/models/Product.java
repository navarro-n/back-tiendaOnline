package com.tienda.app.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    private String imagen;

    private String descripcion;
    @Column(nullable = false)
    private BigDecimal precio;
    @Column(nullable = false)
    private Double tax;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private LocalDateTime fechaProducto = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
}

/*
Product: Representa un producto dentro de la tienda. Debe contener:

Un identificador único.
Un nombre, una imagen y una descripción del producto.
Un precio y el impuesto aplicado.
La moneda en la que se vende el producto (Solo puede ser EUR y USD) podéis añadir otras monedas.
Una referencia al usuario que vende el producto.
La fecha en la que se creó el producto.

 */