package ru.hh.school.jdbc.example;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.Set;

public class CurrencyService {

    private final CurrencyDAO currencyDAO;
    private TransactionTemplate transactionTemplate;


    public CurrencyService(CurrencyDAO currencyDAO, PlatformTransactionManager transactionManager){

        this.currencyDAO = currencyDAO;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public void save(Currency currency) {
        transactionTemplate.execute(transactionStatus -> {
            currencyDAO.insert(currency);
            return null;
        });
    }

    @Transactional
    public Currency get(int currencyId) {
        return transactionTemplate.execute(transactionStatus -> {
            Optional<Currency> currencyOptional = currencyDAO.get(currencyId);
            if (!currencyOptional.isPresent()) {
                throw new IllegalArgumentException("there is no currency with id " + currencyId);
            }
            return currencyOptional.get();
        });
    }

    @Transactional
    public Set<Currency> getAll() {
        return transactionTemplate.execute(transactionStatus -> currencyDAO.getAll());
    }

    @Transactional
    public void update(Currency currency) {
        transactionTemplate.execute(transactionStatus -> {
            currencyDAO.update(currency);
            return null;
        });
    }

    @Transactional
    public void delete(Currency currency) {
        transactionTemplate.execute(transactionStatus -> {
            currencyDAO.delete(currency.id());
            return null;
        });
    }

}
