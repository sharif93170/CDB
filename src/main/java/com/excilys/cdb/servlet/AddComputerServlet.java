package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.cdb.exception.DBException;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3920808878587122754L;

	@Autowired
	ComputerService computerService;
	@Autowired
	CompanyService companyService;

	@Override
	public void init() throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		ctx.getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Company> listCompany = companyService.findAll();
			request.setAttribute("companies", listCompany);
		} catch (SQLException sql) {
			logger.error(sql.getMessage());
		} catch (PremierePageException ppe) {
			logger.error(ppe.getMessage());
		} catch (DernierePageException dpe) {
			logger.error(dpe.getMessage());
		} catch (DBException dbe) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
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

		try {
			computerService.create(
					new Computer.ComputerBuilder(computerName).introduceDate(Date.valueOf(introducedDate).toLocalDate())
							.discontinuedDate(Date.valueOf(discontinuedDate).toLocalDate())
							.company(new Company.CompanyBuilder(idCompany).build()).build());
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} catch (DBException dbe) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
		}

		response.sendRedirect("dashboard");
	}

}