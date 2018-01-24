package ru.hh.school.users;

import org.hibernate.SessionFactory;
import ru.hh.school.TransactionSupportedService;
import ru.hh.school.deposits.Deposit;
import ru.hh.school.deposits.DepositService;

import java.util.Optional;
import java.util.Set;


import static java.util.Objects.requireNonNull;

public class UserService extends TransactionSupportedService {

    private final UserDAO userDAO;
    private final DepositService depositService;

    public UserService(SessionFactory sessionFactory, UserDAO userDAO, DepositService depositService) {
        super(requireNonNull(sessionFactory));
        this.userDAO = requireNonNull(userDAO);
        this.depositService = requireNonNull(depositService);
    }

    public void save(User user) {
        inTransaction(() -> userDAO.save(user));
    }

    public void saveWithDeposit(User user, double initialAmount, int durationInMonths){
        inTransaction(() -> {
            if(user.id() == null){
                save(user);
            }
            depositService.open(user, durationInMonths, initialAmount);
        });
    }

    public User get(int userId) {
        return inTransaction(() -> {
            Optional<User> optionalUser = userDAO.get(userId);
            if (!optionalUser.isPresent()) {
                throw new IllegalArgumentException("there is no user with id " + userId);
            }
            User user = optionalUser.get();
            if(user.isDeleted()){
                throw new IllegalArgumentException("user with id " + userId + " was deleted");
            }
            return user;
        });
    }

    public Set<User> getAll() {
        return inTransaction(userDAO::getAll);
    }

    public void update(User user) {
        inTransaction(() -> userDAO.update(user));
    }

    public double getSummaryMoneyAmount(User user){
        return inTransaction(() -> user.getDeposits().stream().mapToDouble(Deposit::getAmount).sum());
    }

    public void delete(User user) {
        inTransaction(() -> {
            user.setDeleted(true);
            user.getDeposits().forEach(depositService::close);
            update(user);
        });
    }

}
