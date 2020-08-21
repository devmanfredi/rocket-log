package com.rocketlog.controller;

import com.rocketlog.exception.MessageException;
import com.rocketlog.model.entity.User;
import com.rocketlog.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/ap1/v1")
@Api(tags = {"Users"}, description = "Rndpoint para gerenciamento dos usuários")
public class UserController {

    private UserService service;

    @GetMapping("users/{id}")
    private Optional<User> findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping("/users")
    private Page<User> findAll() throws MessageException {
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<User> userList = service.findAll(pageRequest);
        if (userList.isEmpty()) {
            throw new MessageException("Lista Vazia!");
        }

        return userList;
    }
}
