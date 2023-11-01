package com.factor.it.ecommerce.controller;

import com.factor.it.ecommerce.exception.ResourceNotFoundException;
import com.factor.it.ecommerce.model.Client;
import com.factor.it.ecommerce.service.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/client")
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/list")
    @Operation(summary = "Endpoint para obtener clientes, devuelve una lista de clientes.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Lista de clientes.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))}), @ApiResponse(responseCode = "400", description = "Solicitud erronea.", content = @Content), @ApiResponse(responseCode = "500", description = "Error del servidor.", content = @Content)})
    public ResponseEntity<List<Client>> getClients() throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClients());
    }

    @GetMapping("/list_filtered")
    @Operation(summary = "Endpoint para obtener clientes segun si son o no vip, devuelve una lista de clientes filtrada.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Lista de clientes.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))}), @ApiResponse(responseCode = "400", description = "Solicitud erronea.", content = @Content), @ApiResponse(responseCode = "500", description = "Error del servidor.", content = @Content)})
    public ResponseEntity<List<Client>> getAreVipClients(@RequestParam(defaultValue = "true") boolean isVip) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(clientService.getAreVipClients(isVip));
    }

    @GetMapping("/list_filtered/month")
    @Operation(summary = "Endpoint para obtener clientes segun si son o no vip y mes especifico, devuelve una lista de clientes filtrada.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Lista de clientes.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))}), @ApiResponse(responseCode = "400", description = "Solicitud erronea.", content = @Content), @ApiResponse(responseCode = "500", description = "Error del servidor.", content = @Content)})
    public ResponseEntity<List<Client>> getAreVipClientsByMonth(@RequestParam(defaultValue = "true") boolean isVip,@RequestParam(defaultValue = "1") Integer month) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(clientService.vipByMonth(month,isVip));
    }
}