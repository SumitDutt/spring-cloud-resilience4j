package com.microservice.job.service;

import com.microservice.job.bean.Job;
import com.microservice.job.dto.JobDTO;

import java.util.List;

public interface JobServices {
    List<JobDTO> findAll();
    JobDTO jobById(Long id);
    void addJob(Job job);
    boolean jobDeleteById(Long id);
    boolean updateJob(Long id,Job job);
}
