package ru.hh.school.deposits;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class DepositDAO {

    private final SessionFactory sessionFactory;

    public DepositDAO(SessionFactory sessionFactory) {
        this.sessionFactory = requireNonNull(sessionFactory);
    }

    public Optional<Deposit> get(int id){
        Deposit user = (Deposit) session().get(Deposit.class, id);
        return Optional.ofNullable(user);
    }

    public Set<Deposit> getAll(){
        Criteria criteria = session().createCriteria(Deposit.class);
        return new HashSet<Deposit>(criteria.list());
    }

    public void save(Deposit deposit){
        session().persist(deposit);
    }

    public void update(Deposit deposit){
        session().update(deposit);
    }

    public void delete(Deposit deposit){
        session().delete(deposit);
    }

    public void deleteById(int id){
        session().createQuery("DELETE deposit WHERE id = :id") // HQL
                .setInteger("id", id)
                .executeUpdate();
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

}
