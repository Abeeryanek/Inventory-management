package com.abeer.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.abeer.store.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class AbeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbeerApplication.class, args);
	}

}
