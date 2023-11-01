package com.factor.it.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Clase de solicitud para crear carrito.")
public class CartRequestDTO {
    @NotNull(message = "Ingrese un id")
    @Positive(message = "Ingrese un id de cliente valido")
    private Long client_id;
    @NotEmpty(message = "Ingrese al menos un producto con su cantidad")
    private Map<Long,Integer> products;
}
