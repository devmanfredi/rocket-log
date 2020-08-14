package com.rocketlog.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/ap1/v1")
@Api(tags = {"Users"}, description = "Rndpoint para gerenciamento dos usuários")
public class UserController {

}
