package ru.hh.school;

import org.hibernate.SessionFactory;
import ru.hh.school.deposits.DepositDAO;
import ru.hh.school.deposits.DepositService;
import ru.hh.school.users.User;
import ru.hh.school.users.UserDAO;
import ru.hh.school.users.UserService;
import ru.hh.school.utils.HibernateConfigFactory;

public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = createSessionFactory();

        try{
            DepositService depositService = new DepositService(sessionFactory, new DepositDAO(sessionFactory));
            UserService userService = new UserService(sessionFactory, new UserDAO(sessionFactory), depositService);
            User user = userService.get(9);
            depositService.open(user, 10000, 1);
            depositService.open(user, 200000, 5);

            System.out.println(userService.getSummaryMoneyAmount(user));

//            User headHunterz = new User("Head", "Hunterz");
//            System.out.println("persisting " + headHunterz);
//            userService.save(headHunterz);
//            System.out.println("users in db: " + userService.getAll());
//            System.out.println();
//
//            System.out.println("changing first name to 'Tail' via userService.update");
//            headHunterz.setFirstName("Tail");
//            userService.update(headHunterz);
//            System.out.println("users in db: " + userService.getAll());
//            System.out.println();
        } finally {
            sessionFactory.close();
        }
    }

    private static SessionFactory createSessionFactory() {
        return HibernateConfigFactory.prod().buildSessionFactory();
    }

}
