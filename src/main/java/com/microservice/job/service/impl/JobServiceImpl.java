package com.microservice.job.service.impl;

import com.microservice.job.bean.Company;
import com.microservice.job.bean.Job;
import com.microservice.job.dto.JobCompanyDTO;
import com.microservice.job.entity.JobEntity;
import com.microservice.job.repository.JobRepository;
import com.microservice.job.service.JobServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
    RestTemplate restTemplate;

    @Override
    public List<JobCompanyDTO> findAll() {
        List<JobEntity> jobEntityList = jobRepository.findAll();
        return jobEntityList
                .stream()
                .map(this::convertJobCompanyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Job jobById(Long id) {
        Optional<JobEntity> jobEntityOptioal = jobRepository.findById(id);
        return jobEntityOptioal.map(this::convertJobEntityToJob)
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
            // Save updated entity back to the repository
            jobRepository.save(jobEntity);
            return true;
        }
        return false;

    }

    private JobCompanyDTO convertJobCompanyDTO(JobEntity jobEntity) {
        JobCompanyDTO jobCompanyDTO = new JobCompanyDTO();
        Job job = new Job();
        job.setId(jobEntity.getId());
        job.setTitle(jobEntity.getTitle());
        job.setDescription(jobEntity.getTitle());
        job.setLocation(jobEntity.getTitle());
        job.setMaxSalary(jobEntity.getMaxSalary());
        job.setMinSalary(jobEntity.getMinSalary());
        job.setMinSalary(jobEntity.getMinSalary());
        job.setCompanyId(jobEntity.getId());
        jobCompanyDTO.setJob(job);
        jobCompanyDTO.setCompany(getCompanyDetails(jobEntity.getCompanyId()));
        return jobCompanyDTO;
    }

    private Company getCompanyDetails(Long companyId) {
        try {
            return restTemplate.getForObject("http://localhost:8081/companies/" + companyId, Company.class);
        } catch (RestClientException e) {
            return null;
        }
    }

    private Job convertJobEntityToJob(JobEntity jobEntity) {
        Job job = new Job();
        job.setId(jobEntity.getId());
        job.setTitle(jobEntity.getTitle());
        job.setDescription(jobEntity.getTitle());
        job.setLocation(jobEntity.getTitle());
        job.setMaxSalary(jobEntity.getMaxSalary());
        job.setMinSalary(jobEntity.getMinSalary());
        job.setMinSalary(jobEntity.getMinSalary());
        job.setCompanyId(jobEntity.getId());
        return job;
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
