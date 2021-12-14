package com.nobblecrafts.learn.admin;

import com.nobblecrafts.learn.admin.domain.Associate;
import com.nobblecrafts.learn.admin.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * Esse microserviço possui o seu próprio db e ele precisa possuir os associados
 * cadastrados para registrar uma agenda por causa da associação many-to-many
 * entre as duas entidades. O Scheduler irá precisar apenas do DTO das entidades
 * para aplicar a regra de negócio. A princípio, esse serviço irá aplicar todas
 * as regras de negócio preliminares à criação da agenda que é a sua "boundary"
 * de acordo com os princípios teóricos do domain driven design.
 * 
 * @a Este serviço também está recebendo de volta ao final da votação (o que é
 *    contrário à boas práticas) para salvar o resultado da votação.
 *    Na minha opinião, A melhor prática a ser feita seria
 *    que este serviço enviasse a mensagem a um escalonador que procurasse o
 *    melhor
 *    momento para enviar pautas ao scheduler e que o escalonador enviasse as
 *    agendas a outro serviço que ficasse responsável por manter os estados das
 *    votações.
 * 
 */
@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
