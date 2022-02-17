package com.itrex.cache.service;

import com.itrex.cache.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
	
	Optional<Account> findOne(Long id);

	String findFirstNameById(Long id);
	String findLastNameById(Long id);

	List<Account> findAll();

	Account save(Account account);
	Account update(Account account);

	void remove(Long id);
	void removeAll();
}
