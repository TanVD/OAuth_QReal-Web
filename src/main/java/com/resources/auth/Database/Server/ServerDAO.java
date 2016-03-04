package com.resources.auth.Database.Server;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * This is the main class for work with servers tables in database.
 * @author  TanVD
 */
@Service("serverService")
@Transactional
public class ServerDAO {

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retrieves a single server by id.
     */
    public Server get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first
        Server server = (Server) session.get(Server.class, id);
        trans.commit();
        return server;
    }

    /**
     * Retrieves a list of servers by ip.
     * (List for sure...)
     */
    public List get(String ip) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first
        Query query = session.createQuery("FROM Server E WHERE E.ip = :ip");
        query.setParameter("ip", ip);
        List results = query.list();
        trans.commit();
        return results;
    }

    /**
     * Retrieves all servers.
     */
    public List<Server> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first
        Query query = session.createQuery("FROM Server E");
        List<Server> results = query.list();
        trans.commit();
        return results;
    }

    /**
     * Adds a single server.
     */
    public void add(Server server) {
        if (get(server.getIp()).size() > 0) {
            return;
        }
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.save(server);
        trans.commit();

    }

    /**
     * Deletes the server by id.
     */
    public void delete(Integer id) {

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();

        // Retrieve existing person first
        Server person = (Server) session.get(Server.class, id);

        // Delete
        session.delete(person);
        trans.commit();

    }

    /**
     * Edits the server.
     */
    public void edit(Server server) {

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();

        // Retrieve existing person via id
        Server existingPerson = (Server) session.get(Server.class, server.getId());

        // Assign updated values to this person
        existingPerson.setIp(server.getIp());
        existingPerson.setFormat(server.getFormat());

        // Save updates
        session.save(existingPerson);
        trans.commit();

    }

}