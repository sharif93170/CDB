package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.PageValidator;

@Service
public class ComputerService {

	static Logger logger = LoggerFactory.getLogger(ComputerService.class);

	private final DaoComputer daoComputer;
	private final PlatformTransactionManager transactionManager;

	public ComputerService(DaoComputer daoComputer, PlatformTransactionManager transactionManager) {
		this.daoComputer = daoComputer;
		this.transactionManager = transactionManager;
	}

	public int count(String name) {
		return daoComputer.count(name);
	}

	public Computer showDetails(int idComputer) {
		return daoComputer.showDetails(idComputer);
	}

	public <T> List<Computer> findAll() throws PremierePageException, DernierePageException {
		List<Computer> list;
		PageValidator.previousPageValidator();
		list = daoComputer.findAll(Page.getPage(), Page.getPageSize());
		PageValidator.nextPageValidator(list);
		return list;
	}

	public <T> List<Computer> findAll(String name) throws PremierePageException, DernierePageException {
		List<Computer> list;
		PageValidator.previousPageValidator();
		list = daoComputer.findByName(name, Page.getPage(), Page.getPageSize());
		PageValidator.nextPageValidator(list);
		return list;
	}

	public void create(Computer computer) throws DataException {
		ComputerValidator.isValid(computer);
		daoComputer.create(computer);
	}

	public void update(Computer computer) throws DataException {
		ComputerValidator.isValid(computer);
		daoComputer.update(computer);
	}

	public void deleteByName(String nameToDelete) {
		daoComputer.deleteByName(nameToDelete);
	}

	public void deleteById(int idToDelete) {
		daoComputer.deleteById(idToDelete);
	}

	public void deleteSelection(String[] idTab) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				try {
					daoComputer.deleteSelection(idTab);
				} catch (NumberFormatException nfe) {
					logger.error(nfe.getMessage());
				}
			}
		});
	}

}