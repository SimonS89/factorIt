package com.factor.it.ecommerce.controller;

import com.factor.it.ecommerce.exception.ResourceNotFoundException;
import com.factor.it.ecommerce.model.Product;
import com.factor.it.ecommerce.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/product")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    @Operation(summary = "Endpoint para obtener productos, devuelve una lista de products.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Lista de productos.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}), @ApiResponse(responseCode = "400", description = "Solicitud erronea.", content = @Content), @ApiResponse(responseCode = "500", description = "Error del servidor.", content = @Content)})
    public ResponseEntity<List<Product>> getProducts() throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }
}
