package com.factor.it.ecommerce.repository;

import com.factor.it.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("SELECT c FROM Cart c WHERE c.client.id = ?1 AND c.date >= ?2")
    List<Cart> findAllByClient(Long clientId, LocalDate lastMonth);
}
