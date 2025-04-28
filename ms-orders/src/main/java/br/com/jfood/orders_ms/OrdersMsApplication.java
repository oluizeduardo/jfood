package br.com.jfood.orders_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrdersMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersMsApplication.class, args);
    }

}
