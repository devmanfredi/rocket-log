package com.rocketlog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocketlog.builders.UserResquestBuilder;
import com.rocketlog.dto.request.UserRequestDTO;
import com.rocketlog.util.GenerateToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static com.rocketlog.util.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OauthControllerTest {

    private static String URI = "/oauth";

    @Autowired
    private MockMvc mvc;

    @Value("${security.oauth2.client.client-id}")
    private String client;

    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    private ObjectMapper objectMapper = new ObjectMapper();
    private JacksonJsonParser parser = new JacksonJsonParser();
    private String token = "";

    @Before
    public void beforeTests() throws Exception {
        token = GenerateToken.generateToken(mvc, parser, client, secret);
    }

    @Test
    @Transactional
    public void dadoNovoUsuario_quandoRegistrar_entaoDeveRetornarSucesso() throws Exception {
        UserRequestDTO user = UserResquestBuilder.usuarioComum().build();
        ResultActions perform = mvc.perform(post(URI + "/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(convertObjectToJsonBytes(user)))
                .andExpect(status().isCreated());
        perform.andExpect(jsonPath("$.email", is(user.getEmail())));
        perform.andExpect(jsonPath("$.fullName", is(user.getFullName())));

    }

    @Test
    @Transactional
    public void dadoUsuarioComEmailExistente_quandoRegistrar_entaoDeveRetornarErro() throws Exception {

        UserRequestDTO user = UserResquestBuilder.usuarioAdmin().build();

        ResultActions perform = mvc.perform(post(URI + "/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(convertObjectToJsonBytes(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void dadoUsuarioLogado_quandoPesquisarDadosDeUsuarioLogado_entaoDeveRetornarUsuario() throws Exception {
        ResultActions perform = mvc.perform(get(URI + "/self")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
        perform.andExpect(jsonPath("$.email", is("admin@admin.com")));
    }

}
