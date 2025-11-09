package com.fleet_manager.veloe.repository;

import com.fleet_manager.veloe.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByDocument(String document);

    boolean existsByDocument(String document);

    Optional<Customer> findByName(String name);
}