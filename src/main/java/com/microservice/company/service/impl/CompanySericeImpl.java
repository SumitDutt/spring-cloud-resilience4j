package com.microservice.company.service.impl;


import com.microservice.company.bean.Company;
import com.microservice.company.entity.CompanyEntity;
import com.microservice.company.repository.CompanyRepository;
import com.microservice.company.service.CompanySerice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanySericeImpl implements CompanySerice {

    private CompanyRepository companyRepository;

    public CompanySericeImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> findAll() {
        List<CompanyEntity> companyEntityList = companyRepository.findAll();
        return companyEntityList
                .stream()
                .map(this::convertCopanyEntityToCompany)
                .collect(Collectors.toList());
    }

    private Company convertCopanyEntityToCompany(CompanyEntity companyEntity) {
        Company company = new Company();
        company.setDescription(companyEntity.getDescription());
        company.setId(companyEntity.getId());
        company.setName(companyEntity.getName());
        return company;
    }


    @Override
    public CompanyEntity addCopany(Company company) {
        return companyRepository.save(convertCompanyToCompanyEntity(company));
    }

    private CompanyEntity convertCompanyToCompanyEntity(Company company) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName(company.getName());
        companyEntity.setDescription(company.getDescription());
        return companyEntity;
    }

    @Override
    public Company companyById(Long id) {
        Optional<CompanyEntity> jobEntityOptioal = companyRepository.findById(id);
        return jobEntityOptioal.map(this::convertCopanyEntityToCompany)
                .orElse(null);
    }

    @Override
    public boolean updateCompany(Long id, Company requestCompany) {
        Optional<CompanyEntity> optionalCopany = companyRepository.findById(id);
        if (optionalCopany.isPresent()) {
            CompanyEntity companyEntity = optionalCopany.get();
            companyEntity.setDescription(requestCompany.getDescription());
            companyEntity.setName(requestCompany.getName());
            // Save updated entity back to the repository
            companyRepository.save(companyEntity);

            return true;
        }
        return false;
    }

    @Override
    public boolean companyDeleteById(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;

    }

    @Override
    public boolean existsById(Long id) {
        return companyRepository.existsById(id);
    }

    @Override
    public CompanyEntity findByCompanyId(Long id) {
        return companyRepository.findById(id).orElse(null);

    }
    @Override
    public CompanyEntity updateCompanyEntity(CompanyEntity companyEntity) {
        return companyRepository.save(companyEntity);

    }
}
