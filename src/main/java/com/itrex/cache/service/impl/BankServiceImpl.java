package com.itrex.cache.service.impl;

import com.itrex.cache.model.Bank;
import com.itrex.cache.repository.BankRepository;
import com.itrex.cache.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@CacheConfig(cacheResolver ="cacheResolver")
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
//    @Cacheable(cacheNames = "bank",cacheManager = "alternateCacheManager")
    @Cacheable
    public Optional<Bank> findOne(Long id) {
        System.out.println("find One called bank's method");
        return bankRepository.findById(id);
    }

    @Override
    public List<Bank> findAll() {
        System.out.println("find all called bank's method");
        return bankRepository.findAll();
    }

}
