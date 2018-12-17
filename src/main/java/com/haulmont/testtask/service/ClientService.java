package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.ClientDao;
import com.haulmont.testtask.model.Client;

import java.util.List;

public class ClientService {

    private static ClientDao clientDao;

    public ClientService() {
        clientDao = new ClientDao();
    }

    public void persist(Client entity) {
        clientDao.openCurrentSessionWithTransaction();
        clientDao.persist(entity);
        clientDao.closeCurrentSessionWithTransaction();
    }

    public void update(Client entity) {
        clientDao.openCurrentSessionWithTransaction();
        clientDao.update(entity);
        clientDao.closeCurrentSessionWithTransaction();
    }

    public Client findById(Long id) {
        clientDao.openCurrentSession();
        Client client = clientDao.findById(id);
        clientDao.closeCurrentSession();
        return client;
    }

    public void delete(Long id) {
        clientDao.openCurrentSessionWithTransaction();
        Client book = clientDao.findById(id);
        clientDao.delete(book);
        clientDao.closeCurrentSessionWithTransaction();
    }

    public List<Client> findAll() {
        clientDao.openCurrentSession();
        List<Client> client = clientDao.findAll();
        clientDao.closeCurrentSession();
        return client;
    }

    public void deleteAll() {
        clientDao.openCurrentSessionWithTransaction();
        clientDao.deleteAll();
        clientDao.closeCurrentSessionWithTransaction();
    }

    public ClientDao clientDao() {
        return clientDao;
    }
}
