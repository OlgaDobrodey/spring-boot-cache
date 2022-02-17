package com.itrex.cache;

import com.itrex.cache.model.Account;
import com.itrex.cache.service.AccountService;
import com.itrex.cache.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheResolver;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableCaching
public class SpringBootCacheApplication implements CommandLineRunner {

    @Autowired
    AccountService accountService;
    @Autowired
    BankService bankService;
    @Autowired
    CacheManager cacheManager;
    @Autowired
    EhCacheCacheManager alternateCacheManager;
    @Autowired
    CacheResolver cacheResolver;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCacheApplication.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println("===================START APP======================");

//        addAccountToCache();
        twoMethodsCache();
//        cachePut();
//        delete();

//        condition();
//        unless();
//
//        caching();

//        alternateCacheManager();
        cacheResolver();

        System.out.println("================= SHUT DOWN APP ====================");
    }

    private void addAccountToCache() {
        System.out.println("=============== ADD TO CACHE =====================");
        System.out.println(accountService.findOne(1000l));
        System.out.println(accountService.findOne(2000l));
        System.out.println("=============== READ CACHE =====================");
        System.out.println(accountService.findOne(1000l));
        System.out.println(accountService.findOne(2000l));
    }

    private void twoMethodsCache() {
        System.out.println("=============== ADD TO CACHE =====================");
        System.out.println(accountService.findFirstNameById(1000l));
        System.out.println(accountService.findLastNameById(1000l));
        System.out.println("=============== READ CACHE =====================");
        System.out.println("=============== GET FIRST NAME =====================");
        System.out.println(accountService.findFirstNameById(1000l));
        System.out.println("=============== GET LAST NAME =====================");
        System.out.println(accountService.findLastNameById(1000l));
    }

    private void cachePut() {
        System.out.println("=============== FIND ALL =====================");
        System.out.println(accountService.findAll());

        System.out.println("=============== ADD TO CACHE =====================");
        System.out.println(accountService.findOne(2000l));

        System.out.println("=============== UPDATE =====================");
        Account account = new Account(2000l, "Natasha", "Ivanova", 10000l);
        System.out.println(accountService.update(account));
        System.out.println("=============== READ CACHE =====================");
        System.out.println(accountService.findOne(2000l));
    }

    private void delete() {
        System.out.println("=============== ADD TO CACHE =====================");
        System.out.println(accountService.findOne(1000l));
        System.out.println(accountService.findOne(2000l));
        System.out.println(accountService.findOne(3000l));
        System.out.println("=============== DELETE ONE =====================");
        accountService.remove(1000l);
        System.out.println("=============== READ CACHE =====================");
        System.out.println(accountService.findOne(1000l));
        System.out.println("=============== DELETE ALL =====================");
        accountService.removeAll();
        System.out.println("=============== READ CACHE =====================");
        System.out.println(accountService.findOne(2000l));
        System.out.println(accountService.findOne(3000l));
    }

    private void condition() {
        System.out.println("=============== CONDITION FALSE =====================");
        System.out.println(accountService.findOne(1000l));

        System.out.println("=============== CONDITION TRUE =====================");
        System.out.println(accountService.findOne(2000l));

        System.out.println("=============== READ =====================");
        System.out.println(accountService.findOne(1000l));
        System.out.println(accountService.findOne(2000l));
    }

    private void unless() {
        System.out.println("=============== UNLESS FALSE =====================");
        Account account = new Account(3000l, "Alena", "Leurdo", 300000l);
        System.out.println(accountService.save(account));
        System.out.println("=============== READ =====================");
        System.out.println(accountService.findOne(3000l));

        System.out.println("=============== UNLESS TRUE =====================");
        Account accountTrue = new Account(5000l, "Misha", "Green", 300000l);
        System.out.println(accountService.save(accountTrue));
        System.out.println("=============== READ =====================");
        System.out.println(accountService.findOne(5000l));
    }

    private void caching() {
        System.out.println("=============== ADD CACHE =====================");
        List<Optional<Account>> accountList = accountService.findAll().stream()
                .map(account -> accountService.findOne(account.getId()))
                .collect(Collectors.toList());
        System.out.println("=============== READ =====================");
        accountList
                .forEach(account -> System.out.println(accountService.findOne(account.get().getId())));
    }

    private void alternateCacheManager() {
        System.out.println("=============== ADD CACHE =====================");
        bankService.findOne(1000l);
        accountService.findOne(1000l);
        System.out.println("=============== READ =====================");
        System.out.println(bankService.findOne(1000l));
        System.out.println(accountService.findOne(1000l));
    }

    private void cacheResolver() {
        System.out.println("=============== ADD CACHE =====================");
        bankService.findOne(1000l);
        accountService.findOne(1000l);
        System.out.println("=============== READ =====================");
        System.out.println(bankService.findOne(1000l));
        System.out.println(accountService.findOne(1000l));
    }

}
