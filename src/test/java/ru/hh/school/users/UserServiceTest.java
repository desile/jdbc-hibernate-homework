package ru.hh.school.users;

import org.junit.Test;
import ru.hh.school.HibernateTestBase;
import ru.hh.school.deposits.Deposit;
import ru.hh.school.deposits.DepositDAO;
import ru.hh.school.deposits.DepositService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest extends HibernateTestBase {

    private static final UserDAO userDAO = new UserDAO(sessionFactory);
    private static final DepositDAO depositDAO = new DepositDAO(sessionFactory);
    private static final DepositService depositService = new DepositService(sessionFactory, depositDAO);
    private static final UserService userService = new UserService(sessionFactory, userDAO, depositService);

    @Test
    public void saveShouldInsertUserInDBAndReturnUserWithId() {

        User user1 = new User("first name 1", "last name 1");
        User user2 = new User("first name 2", "last name 2");

        userService.save(user1);
        userService.save(user2);

        assertEquals("first name 1", user1.getFirstName());
        assertEquals("last name 1", user1.getLastName());
        assertEquals(user1, userService.get(user1.id()));

        assertEquals("first name 2", user2.getFirstName());
        assertEquals("last name 2", user2.getLastName());
        assertEquals(user2, userService.get(user2.id()));
    }

    @Test
    public void getShouldReturnUserById(){
        User user = new User("first name 1", "last name 1");
        userService.save(user);

        User userFromDB = userService.get(user.id());

        assertEquals(user, userFromDB);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getShouldThrowExceptionIfNoUserWithSuchId() {
        int nonExistentUserId = 123;
        userService.get(nonExistentUserId);
    }

    @Test
    public void getAllShouldReturnAllUsers() {

        User user1 = new User("first name 1", "last name 1");
        User user2 = new User("first name 2", "last name 2");
        userService.save(user1);
        userService.save(user2);

        Set<User> usersFromDB = userService.getAll();
        Set<User> initialUsers = new HashSet<>();
        initialUsers.add(user1);
        initialUsers.add(user2);

        assertEquals(initialUsers, usersFromDB);
    }

    @Test
    public void updateUserShouldChangeFields() {

        User user = new User("first name", "last name");
        userService.save(user);

        user.setFirstName("new first name");
        userService.update(user);

        User userFromDB = userService.get(user.id());
        assertEquals("new first name", userFromDB.getFirstName());
        assertEquals(user.getLastName(), userFromDB.getLastName());
    }

    @Test
    public void deleteShouldDeleteUserById() throws Exception {

        User user1 = new User("first name 1", "last name 1");
        User user2 = new User("first name 2", "last name 2");
        userService.save(user1);
        userService.save(user2);

        userService.delete(user1);

        assertTrue(user1.isDeleted());
        assertFalse(user2.isDeleted());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getShouldThrowExceptionIfUserIsDeleted() throws Exception {

        User user1 = new User("first name 1", "last name 1");
        userService.save(user1);
        userService.delete(user1);

        userService.get(user1.id());
    }

    @Test
    public void saveWithDepositShouldSaveUserIfItIsNotExisted(){
        User user1 = new User("first name 1", "last name 1");
        userService.saveWithDeposit(user1, 2000,12);

        assertNotNull(user1.id());
    }

    @Test
    public void saveWithDepositShouldOpenDepositForUser(){
        User user1 = new User("first name 1", "last name 1");
        userService.saveWithDeposit(user1, 2000,12);
        assertEquals(user1.getDeposits().size(), 1);
    }

    @Test
    public void getSummaryMoneyAmountFromDepositShouldBeCorrect(){
        User user1 = new User("first name 1", "last name 1");
        userService.saveWithDeposit(user1, 3000, 1);
        userService.saveWithDeposit(user1, 2000, 1);
        userService.saveWithDeposit(user1, 1000, 1);

        assertEquals((int) userService.getSummaryMoneyAmount(user1), 6000);
    }

    @Test
    public void deleteShouldCloseAndAnnulAllDeposits(){
        User user1 = new User("first name 1", "last name 1");
        userService.save(user1);
        userService.saveWithDeposit(user1, 3000, 1);
        userService.saveWithDeposit(user1, 2000, 1);

        userService.delete(user1);

        for(Deposit userDeposit : user1.getDeposits()){
            assertEquals(userDeposit.getAmount(), 0D);
            assertTrue(userDeposit.isClosed());
        }
    }

}
