package com.excilys.cdb.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerRepository;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Service
@EnableJpaRepositories(basePackages = "com.excilys.cdb.dao")
@Scope("singleton")
public class ComputerService {

	@Autowired
	private ComputerRepository computerRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

	public boolean addComputer(String name, String introduced, String discontinued, int company_id) {
		LocalDate dateIntroduced = this.checkDateIsCorrect(introduced);
		LocalDate dateDiscontinued = this.checkDateIsCorrect(discontinued);
		computerRepository.save(
				new Computer.ComputerBuilder(name).introduceDate(dateIntroduced).discontinuedDate(dateDiscontinued)
						.company(new Company.CompanyBuilder(Long.valueOf(company_id)).build()).build());
		return true;
	}

	public boolean editComputer(int id, String name, String introduced, String discontinued, int company_id) {
		LocalDate dateIntroduced = this.checkDateIsCorrect(introduced);
		LocalDate dateDiscontinued = this.checkDateIsCorrect(discontinued);
		computerRepository.save(new Computer.ComputerBuilder(name).id(Long.valueOf(id)).introduceDate(dateIntroduced)
				.discontinuedDate(dateDiscontinued)
				.company(new Company.CompanyBuilder(Long.valueOf(company_id)).build()).build());
		return true;
	}

	public int getNumberComputers() {
		return (int) computerRepository.count();
	}

	public boolean deleteComputer(String selection) {
		String[] deleteSelected = selection.split(",");
		boolean isDeleteOk = true;

		for (int i = 0; i < deleteSelected.length; i++) {
			computerRepository.deleteById(Integer.valueOf(deleteSelected[i]));
			LOGGER.info("isdeleteok -> " + isDeleteOk);
		}
		return isDeleteOk;
	}

	public ArrayList<Computer> getComputersByLimitAndOffset(int limit, int offset) {
		return computerRepository.findWithLimitOffset(offset, limit);
	}

	public ArrayList<Computer> getComputersByName(String name) {
		return computerRepository.findByName(name);

	}

	public ArrayList<Computer> getComputersByCompanyName(String search) {
		return computerRepository.findByCompanyName(search);
	}

	public LocalDate checkDateIsCorrect(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate;

		if (!date.isEmpty()) {
			try {
				localDate = LocalDate.parse(date, formatter);
				return localDate;
			} catch (DateTimeParseException e) {
				return null;
			}
		} else {
			return null;
		}
	}

}