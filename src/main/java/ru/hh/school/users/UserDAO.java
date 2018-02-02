package ru.hh.school.users;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class UserDAO {

    private final SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = requireNonNull(sessionFactory);
    }

    public Optional<User> get(int id){
        User user = (User) session().get(User.class, id);
        return Optional.ofNullable(user);
    }

    public Set<User> getAll(){
        Criteria criteria = session().createCriteria(User.class);
        criteria.add(Restrictions.eq("deleted", false));
        return new HashSet<User>(criteria.list());
    }

    public void save(User user){
        session().persist(user);
    }

    public void update(User user){
        session().update(user);
    }

    public void delete(User user){
        session().delete(user);
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

}
