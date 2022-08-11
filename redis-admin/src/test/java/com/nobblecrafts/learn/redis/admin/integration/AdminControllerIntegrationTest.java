package com.nobblecrafts.learn.redis.admin.integration;

import java.util.HashSet;
import java.util.List;

import com.nobblecrafts.learn.redis.admin.domain.Agenda;
import com.nobblecrafts.learn.redis.admin.domain.Associate;
import com.nobblecrafts.learn.redis.admin.util.AgendaSupplier;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class AdminControllerIntegrationTest {

  @Autowired
  private TestRestTemplate template;
  @LocalServerPort
  private int port;
  private final AgendaSupplier supplier = new AgendaSupplier();

  @Test
  public void testCreateAgendaCorrect() {
    var agenda = supplier.createAgendaWithoutAssociates(null);
    var objectResponse = template.exchange("/admin/criarpauta", HttpMethod.POST, new HttpEntity<>(agenda),
        Agenda.class);
    log.info("Object Response: {}", objectResponse);
    Assertions.assertThat(objectResponse.getBody()).isNotNull();
    Assertions.assertThat(objectResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void testCreateAgendaIncorrect() {
    var agenda = supplier.createAgendaWithoutAssociates(null);
    var response = template.exchange("/admin/criarpauta", HttpMethod.POST,
        new HttpEntity<>(agenda.withStart(null)), Agenda.class);
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  public void createAssociateCorrect() {
    var associate = supplier.createSetOfAssociates(1).stream().findFirst().get();
    var response = template.exchange("/admin/addassociado", HttpMethod.POST, new HttpEntity<>(associate),
        Associate.class);

    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getId()).isNotNull();
    Assertions.assertThat(response.getBody().getCpf()).isEqualTo(associate.getCpf());
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void createAssociateIncorrect() {
    var associate = supplier.createSetOfAssociates(1).stream().findFirst().get();
    var response = template.exchange("/admin/addassociado", HttpMethod.POST, new HttpEntity<>(associate.withCpf(null)),
        Associate.class);

    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * O ideal aqui seria executar este serviço com um mock
   * para que o amqp retorne ao event handler uma votação.
   * Assim seria possível verificar se a agenda foi fechada.
   */
  @Test
  public void fullVotationTest() {
    this.createAssociatesForIntegratedTest();
  }

  private void createAssociatesForIntegratedTest() {
    var associates = supplier.createSetOfAssociates(50);
    var created = template.exchange("/admin/addassociados", HttpMethod.POST, new HttpEntity<>(associates),
        List.class).getBody();
    
  }

  public void createAgendaForIntegratedTest() {
    var agenda = supplier.createAgendaWithoutAssociates(null);

    List<Associate> associates = template.exchange("/admin/associados", HttpMethod.GET, null,
        List.class).getBody();

    agenda.setAssociates(new HashSet<Associate>(associates));
    var createdAgenda = template.exchange("/admin/criarpauta", HttpMethod.POST, new HttpEntity<>(agenda),
        Agenda.class);
    log.info("\n\n\nAll Created Sample Associates: {}\n\n\n", createdAgenda.getBody());
  }

}
