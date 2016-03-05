package com.resources.auth.Database.Users;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import  org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * This is the main class for work with users tables in database.
 * @author TanVD
 */
@Service("userService")
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
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first
        User person = (User) session.get(User.class, id);
        trans.commit();
        return person;
    }

    /**
     * Retrieves a list of users by login.
     * List here to be sure...
     */
    public List get(String login) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first
        Query query = session.createQuery("FROM User E WHERE E.username = :login");
        query.setParameter("login", login);
        List results = query.list();

        trans.commit();
        return results;
    }

    /**
     * Retrieves a single user by login.
     * (Take first from list of returned)
     */
    public UserDetails loadUserByUsername(String login){
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first

        Query query = session.createQuery("FROM User E WHERE E.username = :login");
        query.setParameter("login", login);
        List<User> results = query.list();
        trans.commit();
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
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first
        Query query = session.createQuery("FROM User E");
        List<User> results = query.list();
        trans.commit();
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
        Transaction trans = session.beginTransaction();
        session.save(person);
        trans.commit();

    }

    /**
     * Deletes an existing user by id
     */
    public void delete(Integer id) {

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();

        // Retrieve existing person first
        User person = (User) session.get(User.class, id);

        // Delete
        session.delete(person);
        trans.commit();

    }

    /**
     * Edits an existing user
     */
    public void edit(User person) {

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();

        // Retrieve existing person via id
        User existingPerson = (User) session.get(User.class, person.getId());

        // Assign updated values to this person
        existingPerson.setUsername(person.getUsername());
        existingPerson.setPassword(person.getPassword());
        existingPerson.setAuthority(person.getAuthority());

        // Save updates
        session.save(existingPerson);
        trans.commit();

    }

}