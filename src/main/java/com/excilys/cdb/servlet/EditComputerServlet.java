package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class EditComputerServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 2713830462205183398L;

	CompanyService companyService;
	ComputerService computerService;
	ComputerDtoMapper mapper;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			companyService = CompanyService.getInstance();
			computerService = ComputerService.getInstance();
			mapper = ComputerDtoMapper.getInstance();

			ComputerDTO computerDto = mapper
					.fromComputer(computerService.showDetails(Integer.parseInt(request.getParameter("id"))));

			request.setAttribute("computerId", computerDto.getId());
			request.setAttribute("computerName", computerDto.getName());
			request.setAttribute("introduced", computerDto.getIntroduced());
			request.setAttribute("discontinued", computerDto.getDiscontinued());
			request.setAttribute("companyId", computerDto.getCompanyId());

			List<Company> listCompanies = companyService.findAll();
			request.setAttribute("companies", listCompanies);

		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} catch (PremierePageException ppe) {
			logger.error("SQL exception : " + ppe.getMessage(), ppe);
		} catch (DernierePageException dpe) {
			logger.error("SQL exception : " + dpe.getMessage(), dpe);
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ComputerDTO computerDto = new ComputerDTO();
		computerDto.setId(request.getParameter("id"));
		computerDto.setName(request.getParameter("computerName"));
		computerDto.setIntroduced(request.getParameter("introduced"));
		computerDto.setDiscontinued(request.getParameter("discontinued"));
		computerDto.setCompanyId(request.getParameter("companyId"));

		computerService = ComputerService.getInstance();

		try {
			computerService.update(Integer.parseInt(request.getParameter("id")), mapper.toComputer(computerDto));
		} catch (NumberFormatException nfe) {
			logger.error("Number format exception : " + nfe.getMessage(), nfe);
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		}

		response.sendRedirect("dashboard");

	}

}
