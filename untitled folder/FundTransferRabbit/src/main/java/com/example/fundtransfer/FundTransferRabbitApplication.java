package com.example.fundtransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DestinationsConfig.class)
public class FundTransferRabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundTransferRabbitApplication.class, args);
	}

}
