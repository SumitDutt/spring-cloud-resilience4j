package com.microservice.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

@SpringBootApplication
@EnableFeignClients
public class JobApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobApplication.class, args);
		int i=1;
		IntStream.range(i,30).parallel().forEach(t->{
			String response = new RestTemplate().getForObject("http://localhost:8082/jobs",String.class);
		});
	}

}
