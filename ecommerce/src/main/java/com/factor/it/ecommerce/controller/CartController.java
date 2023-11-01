package com.factor.it.ecommerce.controller;

import com.factor.it.ecommerce.dto.request.CartRequestDTO;
import com.factor.it.ecommerce.exception.ResourceNotFoundException;
import com.factor.it.ecommerce.exception.StockNotAvailableException;
import com.factor.it.ecommerce.model.Cart;
import com.factor.it.ecommerce.service.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/cart")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    @Operation(summary = "Endpoint para crear un carrito, se espera CartRequestDTO.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Carrito creado correctamente.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CartRequestDTO.class))}), @ApiResponse(responseCode = "400", description = "Solicitud erronea.", content = @Content), @ApiResponse(responseCode = "500", description = "Error del servidor.", content = @Content)})
    public ResponseEntity<Cart> createCart(@Valid @RequestBody CartRequestDTO cartRequestDTO) throws ResourceNotFoundException, StockNotAvailableException {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.save(cartRequestDTO));
    }
}
