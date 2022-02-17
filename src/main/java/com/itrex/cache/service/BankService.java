package com.itrex.cache.service;

import com.itrex.cache.model.Bank;

import java.util.List;
import java.util.Optional;

public interface BankService {
	
	Optional<Bank> findOne(Long id);
	List<Bank> findAll();

}
