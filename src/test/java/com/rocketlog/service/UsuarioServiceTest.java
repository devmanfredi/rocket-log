package com.rocketlog.service;

import com.rocketlog.builders.UserBuilder;
import com.rocketlog.mapper.UserMapper;
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

}
