package com.rocketlog.service;

import com.rocketlog.dto.request.UserRequestDTO;
import com.rocketlog.exception.MessageException;
import com.rocketlog.mapper.UserMapper;
import com.rocketlog.model.entity.User;
import com.rocketlog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService extends AbstractService<UserRepository, User, UUID> {
    public UserService(UserRepository repository) {
        super(repository);
    }

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private BCryptPasswordEncoder bCrypt;


    @Transactional
    public User save(UserRequestDTO userRequestDTO) throws MessageException {
        validEmailExists(userRequestDTO.getEmail());
        User user = mapper.map(userRequestDTO);
        user.setPassword(bCrypt.encode(userRequestDTO.getPassword()));
        user.setCreatedDate(LocalDateTime.now());
        user = repository.saveAndFlush(user);
        customerService.save(user);
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User update(UUID id, UserRequestDTO userRequestDTO) throws MessageException {
        User user = repository.findById(id).orElse(null);
        User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getId().equals(userAuth.getId())) {
            throw new IllegalArgumentException("Authenticated user is not the same as informed user");
        }

        if (!user.getEmail().equals(userRequestDTO.getEmail())) {
            validEmailExists(userRequestDTO.getEmail());
        }

        updateUser(user, userRequestDTO);

        return repository.save(user);
    }

    private void updateUser(User user, UserRequestDTO userRequestDTO) {
        user.setFullName(userRequestDTO.getFullName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(bCrypt.encode(userRequestDTO.getPassword()));
        user.setLastModifiedDate(LocalDateTime.now());
    }

    private void validEmailExists(String email) throws MessageException {
        if (isEmailExists(email)) throw new MessageException("Email já cadastrado");
    }

    private Boolean isEmailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

}
