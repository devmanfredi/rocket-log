package com.rocketlog.repository;

import com.rocketlog.builders.CustomerBuilder;
import com.rocketlog.model.entity.Customer;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @MockBean
    private CustomerRepository repository;

    @Test
    public void dadoUsuario_quandoSalvar_deveRetornarDadosDoCustomer() {
        Customer customer = CustomerBuilder.customer().build();

        Mockito.when(repository.save(customer)).thenReturn(customer);

        Customer result = repository.save(customer);

        assertThat(result, Matchers.notNullValue());
        assertThat(result.getId(), Matchers.equalTo(customer.getId()));
    }

}
