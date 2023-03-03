package com.integration.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"com.integration"})
@EnableJpaRepositories(basePackages = {"com.integration"})
@EntityScan(basePackages = {"com.integration"})
public class ViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViewApplication.class, args);
	}

}
