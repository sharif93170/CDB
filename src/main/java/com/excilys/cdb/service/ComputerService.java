package com.excilys.cdb.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

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

	public Computer showDetails(int idComputer) throws SQLException {
		return daoComputer.showDetails(idComputer);
	}

	public <T> List<Computer> findAll() throws SQLException, PremierePageException, DernierePageException {
		return daoComputer.findAll();
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