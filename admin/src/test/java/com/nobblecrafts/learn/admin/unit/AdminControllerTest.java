package com.nobblecrafts.learn.admin.unit;

import java.util.ArrayList;

import com.nobblecrafts.learn.admin.controller.AdminController;
import com.nobblecrafts.learn.admin.domain.Associate;
import com.nobblecrafts.learn.admin.service.AdminService;
import com.nobblecrafts.learn.admin.util.AgendaSupplier;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

/**
 * Estes testes utilizando o mock não fazem muito sentido
 * quando envolvem alguma consulta a um serviço mocado.
 * As excessões do controller no entanto fazem sentido.
 */
@ExtendWith(SpringExtension.class)
@Slf4j
public class AdminControllerTest {

  private AgendaSupplier supplier = new AgendaSupplier();
  private ArrayList<Associate> associates;

  @InjectMocks
  private AdminController controller;

  @Mock
  private AdminService service;

  @BeforeEach
  public void setup() {
    associates = new ArrayList<>(supplier.createSetOfAssociates(50));
    BDDMockito.when(service.getAllAssociates())
        .thenReturn(associates);
    BDDMockito.when(service.addAssociate(ArgumentMatchers.any()))
        .thenReturn(associates.get(0));
  }

  @Test
  public void testMock() {
    var body = controller.getAllAssociates().getBody();
    Assertions.assertThat(body).isNotEmpty();
    Assertions.assertThat(body)
        .anyMatch(a -> associates.get(0).getName() == a.getName());
  }

  /**
   * Bom, isso eu tenho que olhar. Porque eu achei que o
   * 
   * @Valid dentro do controller iria retornar um erro pela
   *        falta do cpf. Mas o mock está bem avacalhado.
   */
  // @Test
  // public void expectErrorWithAssociateWithNoCpf() {
  // var a = associates.get(1).withCpf(null);
  // log.info("\n\n\nTo save {}\n\n\n", a);

  // var response = controller.createAssociate(a);
  // log.info("\n\n\nResponse {}\n\n\n", response);
  // }

}
