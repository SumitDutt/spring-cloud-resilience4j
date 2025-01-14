package com.microservice.company.controller;


import com.microservice.company.bean.Company;
import com.microservice.company.service.CompanySerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanySerice compayServices;

    @GetMapping
    public List<Company> findAll() {
        return compayServices.findAll();
    }

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        compayServices.addCopany(company);
        return new ResponseEntity<>("Add Company Succefully", HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = compayServices.companyById(id);
        if (company != null)
            return new ResponseEntity<>(company, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //@RequestMapping(value = "/jobs/{id}",method =RequestMethod.PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateCompany(@PathVariable Long id, @RequestBody Company company) {
        if (compayServices.updateCompany(id, company))
            return new ResponseEntity<>("Company Updated Succefully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id) {
        if (compayServices.companyDeleteById(id))
            return new ResponseEntity<>("Company Deleted Succefully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
