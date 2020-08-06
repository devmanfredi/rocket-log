package com.rocketlog.service;

import com.rocketlog.builders.UserBuilder;
import com.rocketlog.builders.UserResquestBuilder;
import com.rocketlog.dto.request.UserRequestDTO;
import com.rocketlog.exception.MessageException;
import com.rocketlog.mapper.UserMapper;
import com.rocketlog.model.entity.Customer;
import com.rocketlog.model.entity.User;
import com.rocketlog.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThat;


@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder bCrypt;

    @MockBean
    private CustomerService customerService;


    @Test
    public void dadoUsuarioExistente_quandoPesquisarPorEmail_entaoDeveEncontrarUsuario() {
        User user = UserBuilder.admin().build();
        Mockito.when(userRepository.findByEmail("admin@admin.com")).thenReturn(Optional.of(user));

        User result = userService.findByEmail("admin@admin.com").orElse(null);

        assertThat(result, Matchers.notNullValue());
        assertThat(result.getFullName(), Matchers.equalTo("Admin"));
        assertThat(result.getEmail(), Matchers.equalTo("admin@admin.com"));
    }

    @Test
    public void dadoUsuarioNaoExistente_quandoPesquisarPorEmail_entaoNaoDeveEncontrarUsuario() {
        User user = UserBuilder.admin().build();
        Mockito.when(userRepository.findByEmail("admin@admin.com")).thenReturn(Optional.of(user));
        User result = userService.findByEmail("error@admin.com").orElse(null);
        assertThat(result, Matchers.nullValue());
    }

    @Test
    public void dadoUsuariosExistentes_quandoBuscarTodosUsuarios_entaoDeveRetornarTodosUsuarios() {
        List<User> userList = new ArrayList<>();
        userList.add(UserBuilder.admin().build());
        userList.add(UserBuilder.comum().build());
        userList.add(UserBuilder.rocketlog().build());

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findAll();

        //Assets
        assertThat(result.stream().count(), Matchers.equalTo(3L));
        assertThat(result.stream().filter(i -> i.getEmail().equals("rocketlog@rocketlog.com")).count(), Matchers.equalTo(1L));
        assertThat(result.stream().filter(i -> i.getEmail().equals("erro@gmail.com")).count(), Matchers.equalTo(0L));
        assertThat(result.stream().filter(i -> i.getEmail().equals("comum@comum.com")).count(), Matchers.equalTo(1L));

    }

    @Test
    public void dadoUsuarioRequestDTO_quandoSalvar_entaoDeveRetornarUsuarioSalvo() throws MessageException {
        UserRequestDTO userDTO = UserResquestBuilder.usuarioAdmin().build();
        User user = mapper.map(userDTO);
        String passwordCrypt = bCrypt.encode(userDTO.getPassword());
        user.setPassword(passwordCrypt);

        Customer customer = Customer.builder().user(user).build();

        Mockito.when(bCrypt.encode(userDTO.getPassword())).thenReturn(passwordCrypt);
        Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);
        Mockito.when(customerService.save(user)).thenReturn(customer);

        User result = userService.save(userDTO);

        assertThat(result, Matchers.notNullValue());
        assertThat(result.getId(), Matchers.equalTo(user.getId()));
    }

}
