package com.excilys.cdb.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.validator.PageValidator;

public class ComputerService {

	private static ComputerService computerService = null;
	DaoComputer daoComputer;

	private ComputerService() {
		daoComputer = DaoComputer.getInstance();
	}

	public static ComputerService getInstance() {
		if (computerService == null) {
			computerService = new ComputerService();
		}
		return computerService;
	}

	public int count(String name) throws IOException {
		int result = 0;
		try {
			result = daoComputer.count(name);
		} catch (Exception e) {

		}
		return result;
	}

	public Computer showDetails(int idComputer) throws SQLException {
		return daoComputer.showDetails(idComputer);
	}

	public <T> List<Computer> findAll() throws SQLException, PremierePageException, DernierePageException {
		List<Computer> list;
		PageValidator.previousPageValidator();
		list = daoComputer.findAll(Page.getPage(), Page.getPageSize());
		PageValidator.nextPageValidator(list);
		return list;
	}

	public <T> List<Computer> findAll(String name) throws SQLException, PremierePageException, DernierePageException {
		List<Computer> list;
		PageValidator.previousPageValidator();
		list = daoComputer.findByName(name, Page.getPage(), Page.getPageSize());
		PageValidator.nextPageValidator(list);
		return list;
	}

	public void create(Computer computer) {
		daoComputer.create(computer);
	}

	public void update(int id, Computer computer) {
		daoComputer.update(id, computer);
	}

	public void deleteByName(String nameToDelete) {
		daoComputer.deleteByName(nameToDelete);
	}

	public void deleteById(int idToDelete) {
		daoComputer.deleteById(idToDelete);
	}

}