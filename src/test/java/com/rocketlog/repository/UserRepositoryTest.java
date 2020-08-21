package com.rocketlog.repository;

import com.rocketlog.builders.UserBuilder;
import com.rocketlog.exception.MessageException;
import com.rocketlog.model.entity.User;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThat;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @MockBean
    UserRepository repository;

    @Test
    public void dadoUsuario_quandoSalvar_entaoDeveRetornarEmail() {
        User user = UserBuilder.admin().build();
        Mockito.when(repository.save(user)).thenReturn(user);

        User result = repository.save(user);

        Assert.assertNotNull(result.getEmail());
        Assert.assertThat(result.getEmail(), Matchers.equalTo(user.getEmail()));

    }

    @Test
    public void dadoUsuario_quandoSalvar_entaoDeveRetornarId() {
        User user = UserBuilder.adminComId("Heuler").build();
        Mockito.when(repository.save(user)).thenReturn(user);

        User result = repository.save(user);
        Assert.assertNotNull(result.getId());
    }

    @Test
    public void deveRetornarListaDeUsuarios() {
        List<User> userList = new ArrayList<>();
        userList.add(UserBuilder.admin().build());
        userList.add(UserBuilder.comum("Comum").build());
        userList.add(UserBuilder.rocketlog().build());

        Mockito.when(repository.findAll()).thenReturn(userList);

        List<User> result = repository.findAll();
        assertThat(result.stream().count(), Matchers.equalTo(3L));
        assertThat(result.stream().count(), Matchers.equalTo(3L));
    }

    @Test
    public void dadoUsuario_quandoPesquisarPorId_entaoDeveRetornarDados() throws MessageException {
        User user = UserBuilder.admin().build();
        Mockito.when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = repository.findById(user.getId()).orElseThrow(() -> new MessageException("Usuário não encontrado"));

        assertThat(result, Matchers.notNullValue());
        assertThat(result.getFullName(), Matchers.equalTo("Admin"));
        assertThat(result.getEmail(), Matchers.equalTo("admin@admin.com"));
    }
}
