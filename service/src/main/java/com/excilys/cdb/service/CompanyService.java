package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.model.Company;

@Service
public class CompanyService {

	private CompanyDao companyDao;
	private ComputerDao computerDao;

	static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

	private CompanyService(CompanyDao companyDAO, ComputerDao computerDao) {
		this.companyDao = companyDAO;
		this.computerDao = computerDao;
	}

	public Company findById(Long id) {
		return companyDao.findById(id).get();
	}

	public List<Company> findAll() {
		return companyDao.findAll();
	}

	public Page<Company> findLimitNumberOfResult(int pageIndex, int numberOfResultByPage) {
		Pageable pageable = new PageRequest(pageIndex, numberOfResultByPage);
		return companyDao.findAll(pageable);
	}

	public List<Company> findbyName(String name) {
		return companyDao.findByNameContaining(name);
	}

	public int delete(Long id) {
		if (findById(id).getId() == 0) {
			LOGGER.info("Company that you want to delete don't exist");
			return 0;
		} else {
			computerDao.deleteAllByCompanyIdIn(1l);
			companyDao.delete(companyDao.findById(1l).get());
			return 1;
		}
	}

}