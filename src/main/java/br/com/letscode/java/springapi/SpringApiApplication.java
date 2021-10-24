package br.com.letscode.java.springapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringApiApplication.class, args);
	}

}
