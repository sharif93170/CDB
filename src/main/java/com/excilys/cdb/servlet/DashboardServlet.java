package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.Messaging.SyncScopeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DernierePageException;
import com.excilys.cdb.exception.PremierePageException;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

public class DashboardServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7682625742899332286L;

	ComputerService computerService;
	ComputerDtoMapper mapper;
	List<Computer> computer;
	List<Computer> computerPage = new ArrayList<Computer>();
	List<ComputerDTO> computerPageDTO = new ArrayList<ComputerDTO>();
	int computerTotal;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			computerService = ComputerService.getInstance();
			mapper = ComputerDtoMapper.getInstance();

			Page.setPage(request.getParameter("page"), request.getParameter("size"));

			if (request.getParameter("search") == null) {
				computer = computerService.findAll("");
				computerTotal = computerService.count("");
			} else {
				request.setAttribute("search", request.getParameter("search"));
				computer = computerService.findAll(request.getParameter("search"));
				computerTotal = computerService.count(request.getParameter("search"));
			}
			computerPageDTO.clear();
			for (int i = 0; i < computer.size(); i++) {
				computerPageDTO.add(mapper.fromComputer(computer.get(i)));
			}

		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		} catch (PremierePageException ppe) {
			logger.error("SQL exception : " + ppe.getMessage(), ppe);
		} catch (DernierePageException dpe) {
			logger.error("SQL exception : " + dpe.getMessage(), dpe);
		}

		request.setAttribute("computerTotal", computerTotal);
		request.setAttribute("computerPage", computerPageDTO);
		request.setAttribute("pageActual", Page.getPage());
		request.setAttribute("pageSize", Page.getPageSize());

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		computerService = ComputerService.getInstance();
		System.out.println(request.getParameterValues("selection")[0]);
		String[] checkedIds = request.getParameterValues("selection");
		String[] idTab = checkedIds[0].split(",");

		try {
			computerService.deleteSelection(idTab);
		} catch (NumberFormatException nfe) {
			logger.error("SQL exception : " + nfe.getMessage(), nfe);
		} catch (SQLException sql) {
			logger.error("SQL exception : " + sql.getMessage(), sql);
		}

		response.sendRedirect("dashboard");
	}

}