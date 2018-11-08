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

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Company;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CompanyService {

	static Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private static CompanyService companyService = null;
	DaoCompany daoCompany;
	DaoComputer daoComputer;

	@Resource
	private SessionContext context;

	private CompanyService() {
		daoCompany = DaoCompany.getInstance();
		daoComputer = DaoComputer.getInstance();
	}

	public static CompanyService getInstance() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		return companyService;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> List<Company> findAll()
			throws SQLException, PremierePageException, DernierePageException, IOException, DBException {
		List<Company> list;
		try {
			list = daoCompany.findAll();
		} catch (DBException dbe) {
			context.setRollbackOnly();
			throw dbe;
		}
		return list;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(int id) throws IOException, DBException, SQLException {
		try {
			daoComputer.deleteByCompanyId(id);
			daoCompany.delete(id);
		} catch (DBException dbe) {
			context.setRollbackOnly();
			throw dbe;
		}
	}

}