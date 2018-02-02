package ru.hh.school.jdbc.example;

import org.junit.Test;
import ru.hh.school.JdbcTestBase;
import ru.hh.school.users.User;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class CurrencyServiceTest extends JdbcTestBase {


    @Test
    public void saveShouldInsertCurrencyInDBAndReturnUserWithId() {

        Currency currency1 = Currency.create("Ruble", "RUB", new Date(), 56.5f);
        Currency currency2 = Currency.create("Indian Rupee", "IRN", new Date(), 63.2f);

        currencyService.save(currency1);
        currencyService.save(currency2);

        assertEquals("Ruble", currency1.getFullName());
        assertEquals("RUB", currency1.getShortName());
        assertEquals(currency1, currencyService.get(currency1.id()));

        assertEquals("Indian Rupee", currency2.getFullName());
        assertEquals("IRN", currency2.getShortName());
        assertEquals(currency2, currencyService.get(currency2.id()));
    }

}
