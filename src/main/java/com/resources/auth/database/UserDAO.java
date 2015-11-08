package com.resources.auth.database;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import  org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by tanvd on 07.11.15.
 */
@Service("userService")
@Transactional
public class UserDAO{


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retrieves a single user
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

    public List get(String login) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first
        Query query = session.createQuery("SELECT E.username, E.password FROM User E WHERE E.username = :login");
        query.setParameter("login", login);
        List results = query.list();

        trans.commit();
        return results;
    }

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
        User user = results.get(0);
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new UserAuthority("ROLE_USER"));
        user.setAuthorities(authorities);
        return user;
    }

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
     * Adds a new person
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
     * Deletes an existing person
     *
     * @param id the id of the existing person
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
     * Edits an existing person
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

        // Save updates
        session.save(existingPerson);
        trans.commit();

    }



}