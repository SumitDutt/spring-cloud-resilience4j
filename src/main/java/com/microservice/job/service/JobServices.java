package com.microservice.job.service;

import com.microservice.job.bean.Job;
import com.microservice.job.dto.JobCompanyDTO;

import java.util.List;

public interface JobServices {
    List<JobCompanyDTO> findAll();
    Job jobById(Long id);
    void addJob(Job job);
    boolean jobDeleteById(Long id);
    boolean updateJob(Long id,Job job);
}
