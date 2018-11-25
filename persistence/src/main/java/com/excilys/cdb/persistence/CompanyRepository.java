package com.excilys.cdb.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.cdb.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
