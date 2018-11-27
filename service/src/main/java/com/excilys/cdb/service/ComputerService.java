package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.exceptions.CdbException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validator.ComputerValidator;

@Service
public class ComputerService {

	static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
	private ComputerValidator computerValidator = new ComputerValidator();
	private ComputerDao computerDao;

	private ComputerService(ComputerDao computerDao) {
		this.computerDao = computerDao;
	}

	public Computer findById(long id) {
		return computerDao.findById(id).get();
	}

	public List<Computer> findAll() {
		return computerDao.findAll();
	}

	public Computer save(Computer computer) throws CdbException {
		if (computerValidator.verifComputer(computer)) {
			return computerDao.save(computer);
		} else {
			return null;
		}

	}

	public int delete(long id) {
		if (findById(id).getId() == 0) {
			LOGGER.info("Le computer que vous voulez supprimer n'esxiste pas");
			return 0;
		} else {
			computerDao.delete(findById(id));
			return 1;
		}
	}

	public Computer update(Computer computer) throws CdbException {

		if (findById(computer.getId()).getId() != 0 & computerValidator.verifComputer(computer)) {
			computerDao.save(computer);
			return computerDao.save(computer);
		} else {
			LOGGER.info("Le computer que vous voulez modifier n'existe pas");
			return null;
		}

	}

	public long countByNameContaining(String name) {
		return computerDao.countByNameContaining(name);
	}

	public Page<Computer> findByComputerAndCompanyNameLimit(String name, int pageIndex, int numberOfResultByPage) {
		Pageable pageable = new PageRequest(pageIndex, numberOfResultByPage);
		return computerDao.findByNameContainingOrCompanyNameContaining(name, name, pageable);
	}

}