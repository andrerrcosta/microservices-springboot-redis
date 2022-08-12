package com.nobblecrafts.learn.redis.admin.integration;

import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.admin.domain.AssociateDTO;
import com.nobblecrafts.learn.redis.admin.util.AgendaSupplier;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
@Slf4j
public class AdminControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;
    @LocalServerPort
    private int port;
    private final AgendaSupplier supplier = new AgendaSupplier();

    @Test
    public void A1_testCreateAgendaCorrect() {
        var agenda = supplier.createAgendaWithoutAssociatesDTO(null);
        log.info("\n\nAgenda: {}\n\n", agenda);
        var objectResponse = template
                .exchange("/admin/criarpauta",
                        HttpMethod.POST,
                        new HttpEntity<>(agenda),
                        AgendaDTO.class);
        log.info("Object Response: {}", objectResponse);
        Assertions.assertThat(objectResponse.getBody()).isNotNull();
        Assertions.assertThat(objectResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void A2_testCreateAgendaIncorrect() {
        var agenda = supplier.createAgendaWithoutAssociatesDTO(null);
        var response = template
                .exchange("/admin/criarpauta",
                        HttpMethod.POST,
                        new HttpEntity<>(agenda.withStartVotation(null)),
                        AgendaDTO.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void B1_createAssociateCorrect() {
        var associate = supplier.createSetOfAssociatesDTO(1).stream().findFirst().get();
        var response = template.
                exchange("/admin/addassociado",
                        HttpMethod.POST,
                        new HttpEntity<>(associate),
                        AssociateDTO.class);

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getCpf()).isEqualTo(associate.getCpf());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void B2_createAssociateIncorrect() {
        var associate = supplier.createSetOfAssociatesDTO(1).stream().findFirst().get();
        var response = template
                .exchange("/admin/addassociado",
                        HttpMethod.POST,
                        new HttpEntity<>(associate.withCpf(null)),
                        AssociateDTO.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * O ideal aqui seria executar este serviço com um mock
     * para que o amqp retorne ao event handler uma votação.
     * Assim seria possível verificar se a agenda foi fechada.
     */
    @Test
    public void C1_fullVotationTest() {
        this.createAssociatesForIntegratedTest();
        this.createAgendaForIntegratedTest();
    }

    private void createAssociatesForIntegratedTest() {
        var associates = supplier.createSetOfAssociatesDTO(50);
        template
                .exchange("/admin/addassociados",
                        HttpMethod.POST,
                        new HttpEntity<>(associates),
                        AssociateDTO[].class).getBody();
    }

    public void createAgendaForIntegratedTest() {
        var agenda = supplier.createAgendaWithoutAssociatesDTO(null);

        var associates = Arrays.stream(template
                .exchange("/admin/associados",
                        HttpMethod.GET,
                        null,
                        AssociateDTO[].class).getBody())
                .map(AssociateDTO::getId)
                .toList();

        log.info("\n\nASSOCIATES: {}\n\n", associates);


//        log.info("\n\nASSOCIATES-ID: {}\n\n", associatesId);

        agenda.setAssociates(associates.stream().collect(Collectors.toSet()));

        var createdAgenda = template
                .exchange("/admin/criarpauta",
                        HttpMethod.POST,
                        new HttpEntity<>(agenda),
                        AgendaDTO.class);
        log.info("\n\n\nAll Created Sample Associates: {}\n\n\n", createdAgenda.getBody());
    }

}
