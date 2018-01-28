package ru.hh.school.deposits;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.hh.school.TransactionSupportedService;
import ru.hh.school.users.User;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class DepositService extends TransactionSupportedService {

    private final DepositDAO depositDAO;

    public DepositService(SessionFactory sessionFactory, DepositDAO depositDAO) {
        super(requireNonNull(sessionFactory));
        this.depositDAO = requireNonNull(depositDAO);
    }

    public void save(Deposit deposit) {
        inTransaction(() -> depositDAO.save(deposit));
    }

    public Deposit open(User user, int durationInMonths, double initialAmount) {
        return inTransaction(() -> {
            double interest = calculateInterest(durationInMonths, initialAmount);
            Calendar expirationCalendar = Calendar.getInstance();
            expirationCalendar.setTime(new Date());
            expirationCalendar.add(Calendar.MONTH, durationInMonths);
            Deposit deposit = new Deposit(user, expirationCalendar.getTime(), initialAmount, interest);
            save(deposit);
            user.getDeposits().add(deposit);
            return deposit;
        });
    }

    public Deposit get(int depositId) {
        return inTransaction(() -> {
            Optional<Deposit> optionalDeposit = depositDAO.get(depositId);
            if (!optionalDeposit.isPresent()) {
                throw new IllegalArgumentException("there is no deposit with id " + depositId);
            }
            return optionalDeposit.get();
        });
    }

    public Set<Deposit> getAll() {
        return inTransaction(depositDAO::getAll);
    }

    public void update(Deposit deposit) {
        inTransaction(() -> depositDAO.update(deposit));
    }

    public void putMoney(Deposit deposit, double addedAmount) {
        inTransaction(() -> {
            double currentAmount = deposit.getAmount();
            deposit.setAmount(addedAmount + currentAmount);
            update(deposit);
        });
    }

    public void takeMoney(Deposit deposit, double takenAmount) {
        inTransaction(() -> {
            double currentAmount = deposit.getAmount();
            if(currentAmount > takenAmount){
                deposit.setAmount(currentAmount - takenAmount);
            } else {
                throw new IllegalArgumentException("you try to take " + takenAmount + " , but on your deposit only " + currentAmount);
            }
            update(deposit);
        });
    }

    public void close(Deposit deposit) {
        inTransaction(() -> {
            if (deposit.getAmount() > 0.01){
                deposit.setAmount(0D);
            }
            deposit.setClosed(true);
            update(deposit);
        });
    }

    public void delete(Deposit deposit) {
        inTransaction(() -> depositDAO.delete(deposit));
    }


    private double calculateInterest(int durationInMonths, double initalAmount){
        double rawInterest = 3 + durationInMonths * 0.1 + initalAmount / 40000;
        return rawInterest < 7 ? rawInterest : 7;
    }
    
}
