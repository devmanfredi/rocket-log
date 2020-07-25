package com.rocketlog.repository;

import com.rocketlog.builders.UserBuilder;
import com.rocketlog.model.entity.User;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    public void dadoUsuario_quandoSalvar_entaoDeveRetornarEmail() {
        User user = UserBuilder.admin().build();
        User result = repository.save(user);

        Assert.assertNotNull(result.getEmail());
        Assert.assertThat(result.getEmail(), Matchers.equalTo(user.getEmail()));

    }

    @Test
    public void dadoUsuario_quandoSalvar_entaoDeveRetornarId() {
        User user = UserBuilder.admin().build();
        User result = repository.save(user);
        Assert.assertNotNull(result.getId());
    }

    @Test
    public void dadoUsuario_quandoEmailNulo_entaoNaoDeveSalvar() {

    }
}
