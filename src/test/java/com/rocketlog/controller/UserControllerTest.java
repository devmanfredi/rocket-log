package com.rocketlog.controller;

import com.rocketlog.builders.UserBuilder;
import com.rocketlog.model.entity.User;
import com.rocketlog.repository.UserRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    private static String URI = "/ap1/v1";

    @Value("${security.oauth2.client.client-id}")
    private String client;

    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    private String token = "";

    private JacksonJsonParser parser = new JacksonJsonParser();


    @Before
    public void beforeTests() throws Exception {
        token = GenerateToken.generateToken(mvc, parser, client, secret);
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    @Transactional
    public void deveRetornarListaDeUsuariosDTOsComPaginacao() throws Exception {
        User joaozinho = UserBuilder.comum().build();
        User mariazinha = UserBuilder.comum().build();
        User user = UserBuilder.admin().build();

        List<User> userList = new ArrayList<>();
        userList.add(joaozinho);
        userList.add(mariazinha);

        Page<User> page = new PageImpl<>(userList);

        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "detail.timestamp"));
        Mockito.when(securityService.getUserAuthenticated()).thenReturn(user);
        Mockito.when(repository.findAll(pageRequest)).thenReturn(page);

        ResultActions pageResponse = mvc.perform(post(URI + "/users")
                .header("Authorization", token))
                .andExpect(status().isOk());

        pageResponse.andExpect(jsonPath("$.content[0].id", is(joaozinho.getId().toString())));
        pageResponse.andExpect(jsonPath("$.content[1].id", is(mariazinha.getId().toString())));

    }
}
