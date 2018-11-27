package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;

@Repository
public interface CompanyDao extends PagingAndSortingRepository<Company, Long> {

	public Optional<Company> findById(Long id);

	public List<Company> findAll();

	public Page<Company> findAll(Pageable pageable);

	public List<Company> findByNameContaining(String name);

	public Long deleteById(Long id);

	public Long deleteAllByIdIn(Iterable<Long> ids);

}