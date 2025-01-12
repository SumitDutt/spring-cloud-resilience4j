package com.microservice.job.controller;
import com.microservice.job.bean.Job;
import com.microservice.job.dto.JobCompanyDTO;
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
    public List<JobCompanyDTO> findAll() {
        return jobServices.findAll();
    }

    @PostMapping
    public ResponseEntity<String> addJob(@RequestBody Job job) {
        jobServices.addJob(job);
        return new ResponseEntity<>("Add Job Succefully", HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobServices.jobById(id);
        if (job != null)
            return new ResponseEntity<>(job, HttpStatus.OK);
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
