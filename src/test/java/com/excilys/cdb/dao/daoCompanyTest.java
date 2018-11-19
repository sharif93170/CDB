package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.model.Company;

public class daoCompanyTest {

	private DaoCompany dc;

//	@Before
//	public void setUp() {
//		dc = DaoCompany.getInstance();
//	}
//
//	@After
//	public void tearDown() {
//		dc = null;
//	}
//
//	@Test
//	public void testFindAllEquals() {
//		try {
//			ArrayList<Company> result = dc.findAll();
//			Company company = new Company();
//			for (int i = 0; i < result.size(); i++) {
//				assertEquals(company.getClass(), result.get(i).getClass());
//			}
//		} catch (Exception e) {
//			fail("Exception inattendue");
//		}
//	}

//	@Test
//	public void testFindAllNotNull() throws IOException, DBException {
//		try {
//			Assert.assertNotNull(dc.findAll());
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			fail("Aucune company trouvée !");
//		}
//	}

}
