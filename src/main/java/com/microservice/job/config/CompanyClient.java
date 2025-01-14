package com.microservice.job.config;

import com.microservice.job.bean.Company;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;


@FeignClient(name="COMPANY-SERVICE")
public interface CompanyClient {
   @GetMapping("/companies/{id}")
   Company getCompany(@PathVariable Long id);
}
