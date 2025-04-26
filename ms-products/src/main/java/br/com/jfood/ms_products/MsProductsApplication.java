package br.com.jfood.ms_products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProductsApplication.class, args);
	}

}
