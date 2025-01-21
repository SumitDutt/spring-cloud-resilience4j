package com.microservice.job.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.job.bean.Company;
import com.microservice.job.bean.Job;
import com.microservice.job.bean.Review;
import com.microservice.job.config.CompanyClient;
import com.microservice.job.config.ReviewClient;
import com.microservice.job.dto.JobDTO;
import com.microservice.job.entity.JobEntity;
import com.microservice.job.repository.JobRepository;
import com.microservice.job.service.JobServices;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobServices {
    private JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Autowired
    CompanyClient companyClient;
    @Autowired
    ReviewClient reviewClient;
    @Autowired
    ObjectMapper objectMapper;

    int attempt =0;

    @Override
     // @CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    //@Retry(name="ompanyBreaker",fallbackMethod = "companyBreakerFallback")
   // @RateLimiter(name="companyBreaker",fallbackMethod = "companyBreakerFallback")

    public List<JobDTO> findAll() {
        System.out.println("Attempt ="+ ++attempt);

        List<JobEntity> jobEntityList = jobRepository.findAll();
        return jobEntityList
                .stream()
                .map(this::convertJobEntityToJobCompanyDTO)
                .collect(Collectors.toList());
    }
public List<String> companyBreakerFallback(Exception e){

        return Arrays.asList("Dummy");
}
    @Override
    public JobDTO jobById(Long id) {
        Optional<JobEntity> jobEntityOptioal = jobRepository.findById(id);
        return jobEntityOptioal.map(this::convertJobEntityToJobCompanyDTO)
                .orElse(null);

       /* Optional<Job> job = jobs.stream()
                .filter(j -> j.getId() == id)
                .findFirst();
*/
        /*if (job.isPresent()) {
            return job.get();
        }
        return null;*/
    }

    @Override
    @Transactional
    public void addJob(Job job) {
        JobEntity jobEntity = convertJobToJobEntity(job);
        jobRepository.save(jobEntity); // Save the JobEntity to the repository
    }


    @Override
    public boolean jobDeleteById(Long id) {
        //return jobs.removeIf(job -> job.getId() == id);
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean updateJob(Long id, Job reqestJob) {
        /*Optional<Job> optionalJob = jobs.stream()
                .filter(job -> job.getId() == id)
                .findFirst();*/
        Optional<JobEntity> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()) {
            JobEntity jobEntity = optionalJob.get();
            jobEntity.setLocation(reqestJob.getLocation());
            jobEntity.setTitle(reqestJob.getTitle());
            jobEntity.setMaxSalary(reqestJob.getMaxSalary());
            jobEntity.setMinSalary(reqestJob.getMinSalary());
            jobEntity.setDescription(reqestJob.getDescription());
            jobRepository.save(jobEntity);
            return true;
        }
        return false;

    }


    private Company getCompanyDetails(Long companyId) {
        try {
            // return restTemplate.getForObject("http://localhost:8081/companies/" + companyId, Company.class);
            //return restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + companyId, Company.class);

            return companyClient.getCompany(companyId);
            //return companyClient.getCompany(companyId);
        } catch (RestClientException e) {
            return null;
        }
    }

    private List<Review> getReviews(Long companyId) {
        try {
            // return restTemplate.getForObject("http://localhost:8081/companies/" + companyId, Company.class);
            // ResponseEntity<List<Review>> reviewRespose = restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + companyId, HttpMethod.GET,
            //       null, new ParameterizedTypeReference<List<Review>>() {
            //});
            //return reviewRespose.getBody();
            return reviewClient.getReviews(companyId);
        } catch (RestClientException e) {
            return null;
        }
    }

    private JobDTO convertJobEntityToJobCompanyDTO(JobEntity jobEntity) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(jobEntity.getId());
        jobDTO.setTitle(jobEntity.getTitle());
        jobDTO.setDescription(jobEntity.getTitle());
        jobDTO.setLocation(jobEntity.getTitle());
        jobDTO.setMaxSalary(jobEntity.getMaxSalary());
        jobDTO.setMinSalary(jobEntity.getMinSalary());
        jobDTO.setMinSalary(jobEntity.getMinSalary());
        jobDTO.setCompany(getCompanyDetails(jobEntity.getCompanyId()));
        jobDTO.setReviews(getReviews(jobEntity.getCompanyId()));
        return jobDTO;
    }

    private JobEntity convertJobToJobEntity(Job job) {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setTitle(job.getTitle());
        jobEntity.setDescription(job.getDescription());
        jobEntity.setMinSalary(job.getMinSalary());
        jobEntity.setMaxSalary(job.getMaxSalary());
        jobEntity.setCompanyId(job.getCompanyId());
        jobEntity.setLocation(job.getLocation());
        // jobEntity.setCompanyEntity(new CompanyEntity(job.getCompany().getName(), job.getCompany().getName()));
        return jobEntity;
    }

}
