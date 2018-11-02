package com.excilys.cdb.jdbc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ConnexionMySQLTest {

	@Test
	public void connectionTest() {
		try {
			assertNotNull(ConnexionMySQL.getInstance());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
