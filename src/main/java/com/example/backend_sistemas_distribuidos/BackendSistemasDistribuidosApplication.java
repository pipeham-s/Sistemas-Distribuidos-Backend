package com.example.backend_sistemas_distribuidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.backend_sistemas_distribuidos.persistance")
@EntityScan(basePackages = "com.example.backend_sistemas_distribuidos.business.entities")
public class  BackendSistemasDistribuidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSistemasDistribuidosApplication.class, args);
	}
}

