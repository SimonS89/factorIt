package com.factor.it.ecommerce.model;

import com.factor.it.ecommerce.util.CartType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "carts_products", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    private double totalCost;
    private LocalDate date;
}
