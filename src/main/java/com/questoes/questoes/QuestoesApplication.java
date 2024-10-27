package com.questoes.questoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //Habilitar o suporte a tarefas agendadas
public class QuestoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestoesApplication.class, args);
	}

}
