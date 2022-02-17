package com.itrex.cache.service.impl;

import com.itrex.cache.annotation.SlowService;
import com.itrex.cache.model.Account;
import com.itrex.cache.repository.AccountRepository;
import com.itrex.cache.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@CacheConfig(cacheResolver ="cacheResolver")
public class AccountServiceImpl implements AccountService {

    final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
//    @Cacheable(cacheNames = "accountCache", key = "#id",condition = "#id>1000")
    @Caching(cacheable = {
            @Cacheable( key = "#id"),
            @Cacheable(cacheNames = "otherCache", key = "#id", unless = "#result?.balance <= 20000")
    })
    public Optional<Account> findOne(Long id) {
        System.out.println("find One called");
        return accountRepository.findById(id);
    }

    @Override
   @SlowService
    public String findFirstNameById(Long id) {
        System.out.println("call find by first name method");
        return accountRepository.findById(id)
                .get()
                .getFirstName();
    }

    @Override
    @SlowService
    public String findLastNameById(Long id) {
        System.out.println("call find by last name method");
        return accountRepository.findById(id)
                .get()
                .getLastName();
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @CachePut(key = "#account.id", unless = "#result.lastName.contains('Green')")
    public Account save(Account account) {
        System.out.println("method save called");
        return accountRepository.save(account);
    }

    @Override
    @CachePut(key = "#account.id")
    public Account update(Account account) {
        System.out.println("method update called");
        Account accountUpdate = accountRepository.getById(account.getId());
        accountUpdate.setFirstName(account.getFirstName());
        accountUpdate.setLastName(account.getLastName());
        accountUpdate.setBalance(account.getBalance());
        return accountRepository.save(accountUpdate);
    }

    @Override
    @CacheEvict()
    public void remove(Long id) {
        System.out.println("method remove called");
    }

    @Override
    @CacheEvict(allEntries = true)
    public void removeAll() {
        System.out.println("method remove all called");
    }

}
