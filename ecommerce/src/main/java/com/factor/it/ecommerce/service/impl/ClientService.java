package com.factor.it.ecommerce.service.impl;

import com.factor.it.ecommerce.exception.ResourceNotFoundException;
import com.factor.it.ecommerce.model.Client;
import com.factor.it.ecommerce.repository.ClientRepository;
import com.factor.it.ecommerce.service.IClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client findById(Long id) throws ResourceNotFoundException {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client with id " + id+" not found."));
    }

    @Override
    public List<Client> getClients() throws ResourceNotFoundException {
        List<Client> clients = clientRepository.findAll();
        checkIfListIsEmpty(clients, "No existen clientes");
        return clients;
    }

    @Override
    public List<Client> getAreVipClients(boolean isVip) throws ResourceNotFoundException {
        List<Client> vipClients = clientRepository.getVipClients(isVip);
        checkIfListIsEmpty(vipClients, "No existen clientes");
        return vipClients;
    }

    @Override
    public List<Client> vipByMonth(Integer month, boolean isVip) throws ResourceNotFoundException {
        List<Client> areVipClients = clientRepository.getVipClientsByMonth(isVip, month);
        checkIfListIsEmpty(areVipClients, "No existen clientes con esos parametros de busqueda");
        return areVipClients;
    }

    private void checkIfListIsEmpty(List<Client> clients, String message) throws ResourceNotFoundException {
        if (clients.isEmpty()) {
            throw new ResourceNotFoundException(message);
        }
    }
}