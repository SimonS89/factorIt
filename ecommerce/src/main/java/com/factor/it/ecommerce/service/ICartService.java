package com.factor.it.ecommerce.service;

import com.factor.it.ecommerce.dto.request.CartRequestDTO;
import com.factor.it.ecommerce.exception.ResourceNotFoundException;
import com.factor.it.ecommerce.exception.StockNotAvailableException;
import com.factor.it.ecommerce.model.Cart;

public interface ICartService {

     Cart save(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException, StockNotAvailableException;
}
