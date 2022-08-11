package com.nobblecrafts.learn.redis.dbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A idéia deste serviço é ser stateless e genérico porque
 * ele é apenas um scheduler que recebe uma task e a executa.
 */
@SpringBootApplication
public class DbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbsApplication.class, args);
	}

}
