package com.factor.it.ecommerce.service.impl;

import com.factor.it.ecommerce.exception.ResourceNotFoundException;
import com.factor.it.ecommerce.exception.StockNotAvailableException;
import com.factor.it.ecommerce.model.Product;
import com.factor.it.ecommerce.repository.ProductRepository;
import com.factor.it.ecommerce.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product productAvailable(Long id, Integer quantity) throws ResourceNotFoundException, StockNotAvailableException {
        Product product = getProductById(id);
        checkStockAvailability(product, quantity);
        return product;
    }

    @Override
    public List<Product> getProducts() throws ResourceNotFoundException {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) throw new ResourceNotFoundException("No products in db.");
        return products;
    }

    private Product getProductById(Long id) throws ResourceNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));
    }

    private void checkStockAvailability(Product product, Integer quantity) throws StockNotAvailableException {
        if (product.getStock() < quantity) {
            throw new StockNotAvailableException(product.getTitle() + " out of stock.");
        }
    }
}
