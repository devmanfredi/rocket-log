package com.rocketlog.controller;

import com.rocketlog.builders.UserBuilder;
import com.rocketlog.model.entity.User;
import com.rocketlog.service.SecurityService;
import com.rocketlog.service.UserService;
import com.rocketlog.util.GenerateToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
    public void dadoUsuario_quandoPesquisar_deveRetornarId() throws Exception {
        User user = UserBuilder.admin().build();
        User result = service.save(user);

        ResultActions perform = mvc.perform(get(URI + "/users/" + result.getId())
                .header("Authorization", token))
                .andExpect(status().isOk());

        perform.andExpect(jsonPath("$.id", is(user.getId().toString())));

    }
}
