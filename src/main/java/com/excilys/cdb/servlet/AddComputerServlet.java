package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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
		} catch (SQLException sql) {
			sql.printStackTrace();
			;
		} catch (PremierePageException pp) {
			pp.printStackTrace();
		} catch (DernierePageException dp) {
			dp.printStackTrace();
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

//		if (!introducedDate.equals("")) {
//			computer.setIntroducedDate(Date.valueOf(introducedDate).toLocalDate());
//		} else {
//			computer.setIntroducedDate(null);
//		}
//		if (!discontinuedDate.equals("")) {
//			computer.setDiscontinuedDate(Date.valueOf(discontinuedDate).toLocalDate());
//		} else {
//			computer.setDiscontinuedDate(null);
//		}

		computerService = ComputerService.getInstance();
		computerService.create(
				new Computer.ComputerBuilder(computerName).introduceDate(Date.valueOf(introducedDate).toLocalDate())
						.discontinuedDate(Date.valueOf(discontinuedDate).toLocalDate())
						.company(new Company.CompanyBuilder(idCompany).build()).build());

		response.sendRedirect("dashboard");
	}

}