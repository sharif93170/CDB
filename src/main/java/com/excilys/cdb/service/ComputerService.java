package com.excilys.cdb.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.validator.PageValidator;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ComputerService {

	static Logger logger = LoggerFactory.getLogger(ComputerService.class);

	private static ComputerService computerService = null;
	DaoComputer daoComputer;

	@Resource
	private SessionContext context;

	private ComputerService() {
		daoComputer = DaoComputer.getInstance();
	}

	public static ComputerService getInstance() {
		if (computerService == null) {
			computerService = new ComputerService();
		}
		return computerService;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int count(String name) throws IOException {
		int result = 0;
		try {
			result = daoComputer.count(name);
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		}
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Computer showDetails(int idComputer) throws SQLException, IOException {
		return daoComputer.showDetails(idComputer);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> List<Computer> findAll() throws SQLException, PremierePageException, DernierePageException, IOException {
		List<Computer> list;
		PageValidator.previousPageValidator();
		list = daoComputer.findAll(Page.getPage(), Page.getPageSize());
		PageValidator.nextPageValidator(list);
		return list;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> List<Computer> findAll(String name)
			throws SQLException, PremierePageException, DernierePageException, IOException {
		List<Computer> list;
		PageValidator.previousPageValidator();
		list = daoComputer.findByName(name, Page.getPage(), Page.getPageSize());
		PageValidator.nextPageValidator(list);
		return list;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(Computer computer) throws IOException, SQLException {
		daoComputer.create(computer);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(int id, Computer computer) throws IOException, SQLException {
		daoComputer.update(id, computer);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteByName(String nameToDelete) throws IOException, SQLException {
		daoComputer.deleteByName(nameToDelete);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteById(int idToDelete) throws IOException, SQLException {
		daoComputer.deleteById(idToDelete);
	}

//	public void deleteSelection(String[] idTab) throws NumberFormatException, IOException, SQLException {
//		daoComputer.deleteSelection(idTab);
//	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSelection(String[] idTab) throws IOException, NumberFormatException, SQLException {
		for (int i = 0; i < idTab.length; i++) { 
			if (!("".equals(idTab[i]))) {
				System.out.println(idTab[i]);
				daoComputer.deleteById(Integer.parseInt(idTab[i]));
			}
		}
	}

}