package com.rocketlog.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService {
    public UserService(JpaRepository repository) {
        super(repository);
    }
}
