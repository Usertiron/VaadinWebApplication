package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class ClientDao implements DaoInterface<Client, Long> {

    private Session currentSession;
    private Transaction currentTransaction;

    public ClientDao() {
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
            Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
            return sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    @Override
    public void persist(Client entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Client entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Client findById(Long id) {
        Client сlient = (Client) getCurrentSession().get(Client.class, id);
        return сlient;
    }

    @Override
    public void delete(Client entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Client> findAll() {
        List<Client> сlients = (List<Client>) getCurrentSession().createQuery("from " + Client.class.getSimpleName()).list();
        return сlients;
    }

    @Override
    public void deleteAll() {
        List<Client> entityList = findAll();
        for (Client entity : entityList){
            delete(entity);
        }
    }
}
