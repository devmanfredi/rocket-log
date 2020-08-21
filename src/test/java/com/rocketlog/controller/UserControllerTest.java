package com.rocketlog.controller;

import com.rocketlog.builders.UserBuilder;
import com.rocketlog.model.entity.User;
import com.rocketlog.service.SecurityService;
import com.rocketlog.service.UserService;
import com.rocketlog.util.GenerateToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    private static String URI = "/ap1/v1";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private UserService service;

    @Value("${security.oauth2.client.client-id}")
    private String client;

    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    private JacksonJsonParser parser = new JacksonJsonParser();
    private String token = "";

    @Before
    public void beforeTests() throws Exception {
        token = GenerateToken.generateToken(mvc, parser, client, secret);
    }

    @Test
    @Transactional
    public void deveRetornarListaDeUsuariosComPaginacao() throws Exception {
        User joaozinho = UserBuilder.comum("Joao").build();
        Thread.sleep(10000);
        User mariazinha = UserBuilder.comum("Maria").build();
        service.save(joaozinho);
        service.save(mariazinha);
        User user = UserBuilder.admin().build();

        List<User> userList = Arrays.asList(joaozinho, mariazinha);
        Page<User> page = new PageImpl<>(userList);

        Mockito.when(securityService.getUserAuthenticated()).thenReturn(user);


        ResultActions pageResponse = mvc.perform(get(URI + "/users")
                .header("Authorization", token))
                .andExpect(status().isOk());

        String idJoaozinho = page.stream().filter(u -> u.getId().equals(joaozinho.getId())).map(u -> u.getId()).collect(Collectors.toList()).get(0).toString();
        String idMariazinha = page.stream().filter(u -> u.getId().equals(mariazinha.getId())).map(u -> u.getId()).collect(Collectors.toList()).get(0).toString();
        pageResponse.andExpect(jsonPath("$.content[0].id", is(idMariazinha)));
        pageResponse.andExpect(jsonPath("$.content[1].id", is(idJoaozinho)));
    }

    @Test
    public void dadoUsuario_quandoPesquisar_deveRetornarId() throws Exception {
        User user = UserBuilder.admin().build();
        User result = service.save(user);

        ResultActions perform = mvc.perform(get(URI + "/users/" + result.getId())
                .header("Authorization", token))
                .andExpect(status().isOk());

        perform.andExpect(jsonPath("$.id", is(user.getId().toString())));

    }
}
