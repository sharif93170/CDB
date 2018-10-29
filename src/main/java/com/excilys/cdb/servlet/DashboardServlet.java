package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

public class DashboardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7682625742899332286L;

	ComputerService computerService;
	List<Computer> computer;
	List<Computer> computerPage;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			computerService = ComputerService.getInstance();
			computer = computerService.findAll();
			computerPage = Page.pagination(computer);

			if (request.getParameter("page") != null) {
				Page.setPage(Integer.parseInt(request.getParameter("page")));
			}

			if (request.getParameter("size") != null) {
				Page.setPageSize(Integer.parseInt(request.getParameter("size")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("computerTotal", computer.size());
		request.setAttribute("computerPage", computerPage);
		request.setAttribute("pageActual", Page.getPage());
		request.setAttribute("pageSize", Page.getPageSize());

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

}