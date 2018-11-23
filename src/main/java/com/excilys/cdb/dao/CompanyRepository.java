package com.excilys.cdb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
