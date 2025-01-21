package com.microservice.job.controller;

import com.microservice.job.bean.Job;
import com.microservice.job.dto.JobDTO;
import com.microservice.job.service.JobServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    JobServices jobServices;

    @GetMapping
    public List<JobDTO> findAll() {
        return jobServices.findAll();
    }

    @PostMapping
    public ResponseEntity<String> addJob(@RequestBody Job job) {
        jobServices.addJob(job);
        return new ResponseEntity<>("Add Job Succefully", HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
        JobDTO jobDTO = jobServices.jobById(id);
        if (jobDTO != null)
            return new ResponseEntity<>(jobDTO, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //@RequestMapping(value = "/jobs/{id}",method =RequestMethod.PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateJob(@PathVariable Long id, @RequestBody Job job) {
        if (jobServices.updateJob(id, job))
            return new ResponseEntity<>("Job Updated Succefully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id) {
        if (jobServices.jobDeleteById(id))
            return new ResponseEntity<>("Job Deleted Succefully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
