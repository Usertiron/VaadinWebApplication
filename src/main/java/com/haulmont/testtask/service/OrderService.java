package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.OrderDao;
import com.haulmont.testtask.model.Order;

import java.util.List;

public class OrderService {

    private static OrderDao orderService;

    public OrderService() {
        orderService = new OrderDao();
    }

    public void persist(Order entity) {
        orderService.openCurrentSessionwithTransaction();
        orderService.persist(entity);
        orderService.closeCurrentSessionwithTransaction();
    }

    public void update(Order entity) {
        orderService.openCurrentSessionwithTransaction();
        orderService.update(entity);
        orderService.closeCurrentSessionwithTransaction();
    }

    public Order findById(Long id) {
        orderService.openCurrentSession();
        Order order= orderService.findById(id);
        orderService.closeCurrentSession();
        return order;
    }

    public void delete(Long id) {
        orderService.openCurrentSessionwithTransaction();
        Order order = orderService.findById(id);
        orderService.delete(order);
        orderService.closeCurrentSessionwithTransaction();
    }

    public List<Order> findAll() {
        orderService.openCurrentSession();
        List<Order> orders = orderService.findAll();
        orderService.closeCurrentSession();
        return orders;
    }

    public void deleteAll() {
        orderService.openCurrentSessionwithTransaction();
        orderService.deleteAll();
        orderService.closeCurrentSessionwithTransaction();
    }

    public OrderDao orderDao() {
        return orderService;
    }
}
