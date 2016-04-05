package com.resources.auth.Database.Users;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.pool2.proxy.CglibProxySource;
import org.hibernate.Query;
import org.hibernate.Session;
import  org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * This is the main class for work with users tables in database.
 * Because of not very complex logic i decided do not create separate userService
 * @author TanVD
 */
//FIXME it may be needed to change transactional settings here (another version of propagation setting)
    //NOTE for some reason proxyMode not working on @EnableTransactionManager level
@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class UserDAO {


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retrieves a single user by id
     */
    public User get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        User person = session.get(User.class, id);
        return person;
    }

    /**
     * Retrieves a list of users by login.
     * List here to be sure...
     */
    public List get(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User E WHERE E.username = :login");
        query.setParameter("login", login);
        List results = query.list();
        return results;
    }

    /**
     * Retrieves a single user by login.
     * (Take first from list of returned)
     */
    public UserDetails loadUserByUsername(String login){
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM User E WHERE E.username = :login");
        query.setParameter("login", login);
        List<User> results = query.list();
        if (results.size() > 1 || results.size() == 0) {
            return null;
        }
        return results.get(0);
    }

    /**
     * Retrieves all users from database.
     */
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User E");
        List<User> results = query.list();
        return results;
    }

    /**
     * Adds a new user
     */
    public void add(User person) {
        if (get(person.getUsername()).size() > 0) {
            return;
        }
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    /**
     * Deletes an existing user by id
     */
    public void delete(Integer id) {

        Session session = sessionFactory.getCurrentSession();
        User person = session.get(User.class, id);
        session.delete(person);
    }

    /**
     * Edits an existing user
     */
    public void edit(User person) {
        Session session = sessionFactory.getCurrentSession();
        User existingPerson = session.get(User.class, person.getId());
        existingPerson.setUsername(person.getUsername());
        existingPerson.setPassword(person.getPassword());
        existingPerson.setAuthorities(person.getAuthorities());
        session.save(existingPerson);
    }

}