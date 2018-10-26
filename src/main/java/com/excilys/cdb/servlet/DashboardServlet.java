package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

public class DashboardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7682625742899332286L;
	
	ComputerService computerService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			computerService = ComputerService.getInstance();
			List<Computer> computer = computerService.findAll(1);
			request.setAttribute("computerTotal", computer.size());
			request.setAttribute("computer", computer);
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
int i=100;
int j=200;
if(i && j <100) {
	System.out.println();
}
	
	}

}
