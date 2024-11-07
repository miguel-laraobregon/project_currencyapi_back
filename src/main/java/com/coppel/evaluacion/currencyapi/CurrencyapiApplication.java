package com.coppel.evaluacion.currencyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CurrencyapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyapiApplication.class, args);

		System.out.println("Hola mundo");
	}

}
