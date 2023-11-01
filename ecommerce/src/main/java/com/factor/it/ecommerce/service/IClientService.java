package com.factor.it.ecommerce.service;

import com.factor.it.ecommerce.exception.ResourceNotFoundException;
import com.factor.it.ecommerce.model.Client;

import java.util.List;

public interface IClientService {
    Client findById(Long id) throws ResourceNotFoundException;

    List<Client> getClients() throws ResourceNotFoundException;
    List<Client> getAreVipClients(boolean isVip) throws ResourceNotFoundException;
    List<Client> vipByMonth (Integer month, boolean isVip) throws ResourceNotFoundException;
}
