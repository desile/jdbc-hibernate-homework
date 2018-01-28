package ru.hh.school;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.hh.school.jdbc.example.CurrencyDAO;
import ru.hh.school.jdbc.example.CurrencyService;

public abstract class JdbcTestBase {

    protected static EmbeddedDatabase database;
    protected static CurrencyService currencyService;
    private static JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void setUpDBTestBaseClass() throws Exception {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create-tables.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(database);
        currencyService = new CurrencyService(new CurrencyDAO(database), new DataSourceTransactionManager(database));
    }

    @After
    public void tearDownDBTestBase() throws Exception {
        jdbcTemplate.update("DELETE FROM users");
    }

    @AfterClass
    public static void tearDownDBTestBaseClass() throws Exception {
        database.shutdown();
    }
}

