package com.tienda.app.repositories;

import com.tienda.app.models.Order;
import com.tienda.app.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
