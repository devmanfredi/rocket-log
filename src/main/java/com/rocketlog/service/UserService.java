package com.rocketlog.service;

import com.rocketlog.dto.request.UserRequestDTO;
import com.rocketlog.exception.ResourceExistsException;
import com.rocketlog.mapper.UserMapper;
import com.rocketlog.model.entity.User;
import com.rocketlog.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserService extends AbstractService<UserRepository, User, UUID> {
    public UserService(UserRepository repository) {
        super(repository);
    }

    private CustomerService customerService;
    private UserMapper mapper;
    private BCryptPasswordEncoder bCrypt;


    @Transactional
    public User save(UserRequestDTO userRequestDTO) {
        validEmailExists(userRequestDTO.getEmail());
        User user = mapper.map(userRequestDTO);
        user.setPassword(bCrypt.encode(userRequestDTO.getPassword()));
        user = repository.saveAndFlush(user);
        customerService.save(user);
        return user;
    }

    private void validEmailExists(String email) {
        if (isEmailExists(email)) throw new ResourceExistsException("Email já cadastrado");
    }

    private Boolean isEmailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }
}
