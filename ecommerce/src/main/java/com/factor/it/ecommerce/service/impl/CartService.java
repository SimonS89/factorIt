package com.factor.it.ecommerce.service.impl;

import com.factor.it.ecommerce.dto.request.CartRequestDTO;
import com.factor.it.ecommerce.exception.ResourceNotFoundException;
import com.factor.it.ecommerce.exception.StockNotAvailableException;
import com.factor.it.ecommerce.model.Cart;
import com.factor.it.ecommerce.model.Client;
import com.factor.it.ecommerce.model.Product;
import com.factor.it.ecommerce.repository.CartRepository;
import com.factor.it.ecommerce.repository.SpecialDateRepository;
import com.factor.it.ecommerce.service.ICartService;
import com.factor.it.ecommerce.service.IClientService;
import com.factor.it.ecommerce.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final IClientService clientService;
    private final IProductService productService;
    private final SpecialDateRepository specialDateRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CartService(CartRepository cartRepository, IClientService clientService, IProductService productService, SpecialDateRepository specialDateRepository) {
        this.cartRepository = cartRepository;
        this.clientService = clientService;
        this.productService = productService;
        this.specialDateRepository = specialDateRepository;
    }

    @Override
    public Cart save(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException, StockNotAvailableException {
        Double totalCost = .0;
        LocalDate today = LocalDate.now();
        List<Product> productList = new ArrayList<>();
        Integer totalItems = 0;
        for (Map.Entry product : cartRequestDTO.getProducts().entrySet()) {
            Product productFound = productService.productAvailable((Long) product.getKey(), (Integer) product.getValue());
            productFound.setStock(productFound.getStock() - (Integer) product.getValue());
            productList.add(productFound);
            totalItems += (Integer) product.getValue();
            totalCost += productFound.getCost() * (Integer) product.getValue();
        }

        List<Cart> clientCarts = cartRepository.findAllByClient(cartRequestDTO.getClient_id(), today.minusMonths(1));
        Client client = clientService.findById(cartRequestDTO.getClient_id());
        boolean wasVip = client.isVip();
        client.setVip(clientCarts.isEmpty() && !client.isVip() ? false : isVipClient(client, clientCarts));
        if (wasVip != client.isVip()) client.setUpdateStatus(LocalDate.now());

        if (totalItems == 4 || totalItems > 10)
            totalCost = calculateCost(client.isVip(), productList, totalCost, totalItems);

        Cart cart = new Cart();
        cart.setClient(client);
        cart.setDate(LocalDate.now());
        cart.setProducts(productList);
        cart.setTotalCost(totalCost);
        return cartRepository.save(cart);
    }

    private boolean isVipClient(Client client, List<Cart> clientCarts) {
        boolean isVip = client.isVip();
        if (!isVip) isVip = clientCarts.stream().mapToDouble(cart -> cart.getTotalCost()).sum() >= 10000;
        return isVip;
    }

    private double calculateCost(boolean isVip, List<Product> products, double cost, Integer totalItems) {
        double totalCost = applyItemDiscount(cost, totalItems);
        if (totalItems > 10) {
            if (isVip) totalCost = applyVipDiscount(products, totalCost);
            else totalCost = applySpecialDateDiscount(totalCost);
        }
        return totalCost;
    }

    private double applyItemDiscount(double cost, Integer totalItems) {
        if (totalItems == 4) return cost * 0.75;
        else return cost;
    }

    private double applyVipDiscount(List<Product> products, double totalCost) {
        double productCost = products.stream().min(Comparator.comparingDouble(Product::getCost)).get().getCost();
        return totalCost - (productCost + 500);
    }

    private double applySpecialDateDiscount(double totalCost) {
        if (specialDateRepository.findByDate(LocalDate.now()).isPresent()) return totalCost - 300;
        else return totalCost - 100;
    }
}
