package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Computer;

@Repository
public interface ComputerDao extends PagingAndSortingRepository<Computer, Long> {

	public Optional<Computer> findById(long id);

	public List<Computer> findAll();

	public Long countByNameContaining(String name);

	public Page<Computer> findByNameContainingOrCompanyNameContaining(String name, String companyName, Pageable p);

	public Long deleteAllByIdIn(Iterable<Long> ids);

	public Long deleteAllByCompanyIdIn(Long id);

}