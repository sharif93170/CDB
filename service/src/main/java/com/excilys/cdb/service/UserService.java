package com.excilys.cdb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.DaoUser;
import com.excilys.cdb.model.User;

@Service
public class UserService implements UserDetailsService {
	Logger logger = LoggerFactory.getLogger(UserService.class);
	private final DaoUser userDao;

	public UserService(DaoUser userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.find(username);
		UserBuilder builder = null;
		if (user == null) {
			throw new UsernameNotFoundException(username);
		} else {
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(user.getPassword());
			builder.roles(user.getRole().getName());
		}
		return builder.build();
	}
}