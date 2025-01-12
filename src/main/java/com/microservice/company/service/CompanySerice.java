package com.microservice.company.service;



import com.microservice.company.bean.Company;
import com.microservice.company.entity.CompanyEntity;

import java.util.List;

public interface CompanySerice {
    List<Company> findAll();

    CompanyEntity addCopany(Company company);

    Company companyById(Long id);

    boolean updateCompany(Long id, Company company);

    boolean companyDeleteById(Long id);

    boolean existsById(Long id);

    CompanyEntity findByCompanyId(Long id);

    CompanyEntity updateCompanyEntity(CompanyEntity companyEntity);
}
