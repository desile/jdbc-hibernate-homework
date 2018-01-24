package ru.hh.school.utils;

import org.hibernate.cfg.Configuration;
import ru.hh.school.deposits.Deposit;
import ru.hh.school.users.User;

public class HibernateConfigFactory {

    public static Configuration prod() {
        return new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Deposit.class);
        }

    private HibernateConfigFactory() {
    }
}
