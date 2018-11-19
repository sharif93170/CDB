package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.model.Company;

@Service
public class CompanyService {

	static Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private final DaoCompany daoCompany;
	private final DaoComputer daoComputer;
	private final PlatformTransactionManager transactionManager;

	public CompanyService(DaoComputer daoComputer, DaoCompany daoCompany,
			PlatformTransactionManager transactionManager) {
		this.daoCompany = daoCompany;
		this.daoComputer = daoComputer;
		this.transactionManager = transactionManager;
	}

	public <T> List<Company> findAll() {
		return daoCompany.findAll();
	}

	public void delete(int id) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				daoComputer.deleteByCompanyId(id);
				daoCompany.delete(id);
			}
		});

	}

}