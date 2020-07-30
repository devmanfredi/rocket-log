package com.rocketlog.repository;

import com.rocketlog.model.entity.Customer;
import com.rocketlog.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByUser(User user);

    Optional<Customer> findByApiKey(UUID apiKey);

}
