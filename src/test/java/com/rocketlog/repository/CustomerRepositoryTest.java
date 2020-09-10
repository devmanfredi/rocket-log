package com.rocketlog.repository;

import com.rocketlog.builders.CustomerBuilder;
import com.rocketlog.builders.UserBuilder;
import com.rocketlog.exception.MessageException;
import com.rocketlog.model.entity.Customer;
import com.rocketlog.model.entity.User;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertThat;

;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @MockBean
    private CustomerRepository repository;

    @Test
    public void dadoUsuario_quandoSalvar_entaoDeveRetornarDadosDoCustomer() {
        Customer customer = CustomerBuilder.customer().build();

        Mockito.when(repository.save(customer)).thenReturn(customer);

        Customer result = repository.save(customer);

        assertThat(result, Matchers.notNullValue());
        assertThat(result.getId(), Matchers.equalTo(customer.getId()));
    }

    @Test
    public void dadoUsuario_quandoPesquisarPeloUsuario_entaoDeveRetornarCustomer() throws MessageException {
        Customer customer = CustomerBuilder.customer().build();
        User user = UserBuilder.comum("Heuler Manfredi").build();

        Mockito.when(repository.findByUser(user)).thenReturn(Optional.of(customer));

        Customer result = repository.findByUser(user).orElseThrow(() -> new MessageException("Customer Não encontrado"));

        assertThat(result, Matchers.notNullValue());
        assertThat(result.getUser().getFullName(), Matchers.equalTo(user.getFullName()));
        assertThat(result.getApiKey(), Matchers.notNullValue());
    }

}
