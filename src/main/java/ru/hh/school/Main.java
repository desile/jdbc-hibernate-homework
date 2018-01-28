package ru.hh.school;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import ru.hh.school.deposits.DepositDAO;
import ru.hh.school.deposits.DepositService;
import ru.hh.school.jdbc.example.Currency;
import ru.hh.school.jdbc.example.CurrencyDAO;
import ru.hh.school.jdbc.example.CurrencyService;
import ru.hh.school.users.User;
import ru.hh.school.users.UserDAO;
import ru.hh.school.users.UserService;
import ru.hh.school.utils.HibernateConfigFactory;
import ru.hh.school.utils.PropertiesFactory;

import javax.sql.DataSource;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import static ru.hh.school.utils.DataSourceFactory.createPGSimpleDataSource;

public class Main {



    public static void main(String[] args) throws IOException {
        SessionFactory sessionFactory = createSessionFactory();
        final Properties properties = PropertiesFactory.load();
        final DataSource jdbcDataSource = createPGSimpleDataSource(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.user"),
                properties.getProperty("jdbc.password")
        );

        try{
            // JDBC
            CurrencyService currencyService = new CurrencyService(new CurrencyDAO(jdbcDataSource), new DataSourceTransactionManager(jdbcDataSource));
            Currency currency = Currency.create("Ruble", "RUB", new Date(), 56.5f);
            currencyService.save(currency);


            // Hibernate

            DepositService depositService = new DepositService(sessionFactory, new DepositDAO(sessionFactory));
            UserService userService = new UserService(sessionFactory, new UserDAO(sessionFactory), depositService);
            User headHunterz = new User("Head", "Hunterz");
            System.out.println("persisting " + headHunterz);
            userService.save(headHunterz);

            userService.saveWithDeposit(headHunterz, 10000, 12);
            userService.saveWithDeposit(headHunterz, 20000, 12);
            userService.saveWithDeposit(headHunterz, 30000, 18);

            System.out.println(userService.getSummaryMoneyAmount(headHunterz));
            userService.delete(headHunterz);

        } finally {
            sessionFactory.close();
        }
    }

    private static SessionFactory createSessionFactory() {
        return HibernateConfigFactory.prod().buildSessionFactory();
    }

}
