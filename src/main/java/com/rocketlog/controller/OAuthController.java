package com.rocketlog.controller;

import com.rocketlog.dto.request.UserRequestDTO;
import com.rocketlog.dto.response.UserResponseDTO;
import com.rocketlog.exception.ApiError;
import com.rocketlog.mapper.UserMapper;
import com.rocketlog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/oauth")
@Api(tags = {"Authentication"}, description = "Endpoint para gerenciamento de usuários")
public class OAuthController {

    private UserService userService;
    private UserMapper userMapper;

    @ApiOperation(
            value = "Cria um novo usuário",
            notes = "Método utilizado para criar um novo usuário."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuário criado", response = UserResponseDTO.class),
            @ApiResponse(code = 400, message = "Requisição mal formatada", response = ApiError.class),
            @ApiResponse(code = 500, message = "Erro na api", response = ApiError.class)

    })
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    private UserResponseDTO save(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userMapper.map(userService.save(userRequestDTO));
    }

}
