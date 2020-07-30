package com.rocketlog.service;

import com.rocketlog.model.entity.User;
import com.rocketlog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends AbstractService<UserRepository, User, UUID> {
    public UserService(UserRepository repository) {
        super(repository);
    }
}
