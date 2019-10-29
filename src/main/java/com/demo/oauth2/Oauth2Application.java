package com.demo.oauth2;

import com.demo.oauth2.dao.JpaAuditingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Oauth2Application {
	@Bean
	public AuditorAware<String> auditorAware() {
		return (AuditorAware<String>) new JpaAuditingConfiguration();
	}

	public static void main(String[] args) {
		SpringApplication.run(Oauth2Application.class, args);
	}

}
