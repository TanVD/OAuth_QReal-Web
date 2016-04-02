package com.resources.auth.Database.Client;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tanvd on 02.04.16.
 */
@Service("clientService")
@Transactional
public class ClientDAO {

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retrieves a single client by id
     */
    public Client get(Integer id) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first
        Client client = (Client) session.get(Client.class, id);
        trans.commit();
        return client;
    }

    /**
     * Retrieves a list of clients by idClient.
     * List here to be sure...
     */
    public List get(String clientId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first
        Query query = session.createQuery("FROM Client E WHERE E.clientId = :clientId");
        query.setParameter("clientId", clientId);
        List results = query.list();

        trans.commit();
        return results;
    }

    /**
     * Retrieves a single client by idClient.
     * (Take first from list of returned)
     */
    public ClientDetails loadClientById(String clientId){
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first

        Query query = session.createQuery("FROM Client E WHERE E.clientId = :clientId");
        query.setParameter("clientId", clientId);
        List<Client> results = query.list();
        trans.commit();
        if (results.size() > 1 || results.size() == 0) {
            return null;
        }
        return results.get(0);
    }

    /**
     * Retrieves all clients from database.
     */
    public List<Client> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        // Retrieve existing person first
        Query query = session.createQuery("FROM Client E");
        List<Client> results = query.list();
        trans.commit();
        return results;
    }

    /**
     * Adds a new client
     */
    public void add(Client client) {
        if (get(client.getClientId()).size() > 0) {
            return;
        }
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.save(client);
        trans.commit();
    }

    /**
     * Deletes an existing client by id
     */
    public void delete(Integer id) {

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();

        // Retrieve existing person first
        Client client = (Client) session.get(Client.class, id);

        // Delete
        session.delete(client);
        trans.commit();

    }

    /**
     * Edits an existing client
     */
    public void edit(Client client) {

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();

        // Retrieve existing person via id
        Client existingClient = (Client) session.get(Client.class, client.getId());

        // Assign updated values to this person
        existingClient.setIdClient(client.getClientId());
        existingClient.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
        existingClient.setAuthorizedGrantTypes(client.getAuthorizedGrantTypes());
        existingClient.setClientSecret(client.getClientSecret());
        existingClient.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
        existingClient.setRegisteredRedirectUri(client.getRegisteredRedirectUri());
        existingClient.setResourceIds(client.getResourceIds());
        existingClient.setScope(client.getScope());
        existingClient.setScoped(client.isScoped());
        existingClient.setSecretRequired(client.isSecretRequired());

        // Save updates
        session.save(existingClient);
        trans.commit();

    }
}
