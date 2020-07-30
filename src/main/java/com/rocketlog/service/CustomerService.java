package com.rocketlog.service;

import com.rocketlog.model.entity.Customer;
import com.rocketlog.model.entity.User;
import com.rocketlog.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService extends AbstractService<CustomerRepository, Customer, UUID> {
    public CustomerService(CustomerRepository repository) {
        super(repository);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Optional<Customer> findByUser(User user) {
        return repository.findByUser(user);
    }

    public Optional<Customer> findByApiKey(UUID apiKey) {
        return repository.findByApiKey(apiKey);
    }

    public Customer save(User user) {
        Customer customer = Customer.builder()
                .user(user)
                .apiKey(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        return repository.save(customer);
    }
}
