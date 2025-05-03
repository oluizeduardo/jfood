package br.com.jfood.ms_orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class OrdersMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersMsApplication.class, args);
    }

}
