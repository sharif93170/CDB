package com.excilys.cdb.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.validator.PageValidator;

@Service
public class ComputerService {

	static Logger logger = LoggerFactory.getLogger(ComputerService.class);

	@Autowired
	private DaoComputer daoComputer;
	@Autowired
	private PlatformTransactionManager transactionManager;

	public int count(String name) throws IOException, DBException, SQLException {
		return daoComputer.count(name);
	}

	public Computer showDetails(int idComputer) throws SQLException, IOException, DBException {
		return daoComputer.showDetails(idComputer);
	}

	public <T> List<Computer> findAll()
			throws SQLException, PremierePageException, DernierePageException, IOException, DBException {
		List<Computer> list;
		PageValidator.previousPageValidator();
		list = daoComputer.findAll(Page.getPage(), Page.getPageSize());
		PageValidator.nextPageValidator(list);
		return list;
	}

	public <T> List<Computer> findAll(String name)
			throws SQLException, PremierePageException, DernierePageException, IOException, DBException {
		List<Computer> list;
		PageValidator.previousPageValidator();
		list = daoComputer.findByName(name, Page.getPage(), Page.getPageSize());
		PageValidator.nextPageValidator(list);
		return list;
	}

	public void create(Computer computer) throws IOException, SQLException, DBException {
		daoComputer.create(computer);
	}

	public void update(int id, Computer computer) throws IOException, SQLException, DBException {
		daoComputer.update(id, computer);
	}

	public void deleteByName(String nameToDelete) throws IOException, SQLException, DBException {
		daoComputer.deleteByName(nameToDelete);
	}

	public void deleteById(int idToDelete) throws IOException, SQLException, DBException {
		daoComputer.deleteById(idToDelete);
	}

	public void deleteSelection(String[] idTab) throws NumberFormatException, IOException, SQLException, DBException {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				try {
					daoComputer.deleteSelection(idTab);
				} catch (NumberFormatException | IOException | SQLException | DBException e) {
					logger.error(e.getMessage());
				}
			}
		});
	}

}