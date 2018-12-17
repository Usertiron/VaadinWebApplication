package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class OrderDao implements DaoInterface<Order, Long> {

    private Session currentSession;
    private Transaction currentTransaction;

    public OrderDao(){}

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
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
    public void persist(Order entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Order entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Order findById(Long id) {
        Order order = (Order) getCurrentSession().get(Order.class, id);
        return order;
    }

    @Override
    public void delete(Order entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> findAll() {
        List<Order> orders = (List<Order>) getCurrentSession().createQuery("from " + Order.class.getSimpleName()).list();
        return orders;
    }

    @Override
    public void deleteAll() {
        List<Order> entityList = findAll();
        for (Order entity : entityList){
            delete(entity);
        }
    }
}
