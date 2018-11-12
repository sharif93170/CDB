package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class daoComputerTest {

	private DaoComputer dc;

//	@Before
//	public void setUp() {
//		dc = DaoComputer.getInstance();
//	}
//
//	@After
//	public void tearDown() {
//		dc = null;
//	}

	@Test
	public void testShowDetails() {
		try {
			Computer cptTest = dc.showDetails(17);
			assertEquals(Long.valueOf("17"), cptTest.getId());
			assertEquals("Apple III Plus", cptTest.getName());
			assertEquals("1983-12-01", cptTest.getIntroducedDate().toString());
			assertEquals("1984-04-01", cptTest.getDiscontinuedDate().toString());
			assertEquals(Long.valueOf("1"), cptTest.getCompany().getId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("Les détails attendus ne sont pas ceux actuels !");
		}
	}

//	@Test
//	public void testFindAllEquals() {
//		try {
//			ArrayList<Computer> result = dc.findAll();
//			Computer cpt = new Computer();
//			for (int i = 0; i < result.size(); i++) {
//				assertEquals(cpt.getClass(), result.get(i).getClass());
//				System.out.println(cpt.getClass());
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

//	@Test
//	public void testFindAllNotNull() {
//		try {
//			assertNotNull(dc.findAll());
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			fail("Aucun computer trouvée !");
//		}
//	}

//	@Test
//	public void testCreate() {
//		LocalDate introduced = LocalDate.of(2018, 01, 01);
//		LocalDate discontinued = LocalDate.of(2018, 12, 31);
//
//		try {
//			Computer cpt = new Computer.ComputerBuilder("TestName").introduceDate(introduced)
//					.discontinuedDate(discontinued).company(new Company.CompanyBuilder(Long.valueOf("1")).build())
//					.build();
//			assertTrue(dc.create(cpt));
//			dc.deleteByName("TestName");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail("Le produit n'a pas été crée");
//		}
//	}

//	@Test
//	public void testUpdate() {
//		LocalDate introduced = LocalDate.of(2018, 01, 01);
//		LocalDate discontinued = LocalDate.of(2018, 12, 31);
//
//		try {
//			Computer cpt = new Computer("TestUpdated", introduced, discontinued, new Company(Long.valueOf("1")));
//			assertTrue(dc.update(577, cpt));
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail("Le produit n'a pas été crée");
//		}
//	}

//	@Test
//	public void testDeleteByName() {
//		LocalDate introduced = LocalDate.of(2018, 01, 01);
//		LocalDate discontinued = LocalDate.of(2018, 12, 31);
//
//		try {
//			Computer cpt = new Computer("ToDelete", introduced, discontinued, new Company(Long.valueOf("1")));
//			dc.create(cpt);
//			assertTrue(dc.deleteByName("ToDelete"));
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail("Le produit n'a pas été crée");
//		}
//	}

}
