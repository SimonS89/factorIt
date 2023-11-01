package com.factor.it.ecommerce.service;

import com.factor.it.ecommerce.exception.ResourceNotFoundException;
import com.factor.it.ecommerce.exception.StockNotAvailableException;
import com.factor.it.ecommerce.model.Product;

import java.util.List;

public interface IProductService {
    Product productAvailable(Long id, Integer quantity) throws ResourceNotFoundException, StockNotAvailableException;
    List<Product> getProducts() throws ResourceNotFoundException;
}
