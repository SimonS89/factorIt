package com.factor.it.ecommerce.repository;

import com.factor.it.ecommerce.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.isVip = ?1")
    List<Client> getVipClients(boolean isVip);

    @Query("SELECT c FROM Client c WHERE c.isVip = ?1 AND FUNCTION('MONTH',c.updateStatus) = ?2")
    List<Client> getVipClientsByMonth(boolean isVip, Integer month);
}
