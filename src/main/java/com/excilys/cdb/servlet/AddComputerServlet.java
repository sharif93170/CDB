package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class AddComputerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3920808878587122754L;
	ComputerService computerService;
	CompanyService companyService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		companyService = CompanyService.getInstance();
		try {
			List<Company> listCompany = companyService.findAll();
			request.setAttribute("companies", listCompany);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (PremierePageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DernierePageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String computerName = request.getParameter("computerName");
		String introducedDate = request.getParameter("introduced");
		String discontinuedDate = request.getParameter("discontinued");
		Long idCompany = Long.parseLong(request.getParameter("companyId"));

		Computer computer = new Computer();
		Company company = new Company(idCompany);

		computer.setName(computerName);
		if (!introducedDate.equals("")) {
			computer.setIntroducedDate(Date.valueOf(introducedDate).toLocalDate());
		} else {
			computer.setIntroducedDate(null);
		}
		if (!discontinuedDate.equals("")) {
			computer.setDiscontinuedDate(Date.valueOf(discontinuedDate).toLocalDate());
		} else {
			computer.setDiscontinuedDate(null);
		}
		computer.setCompany(company);

		computerService = ComputerService.getInstance();
		computerService.create(computer);

		response.sendRedirect("dashboard");
	}

}
