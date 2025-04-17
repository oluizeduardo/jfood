package br.com.jfood.service_registry_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryMsApplication.class, args);
	}

}
