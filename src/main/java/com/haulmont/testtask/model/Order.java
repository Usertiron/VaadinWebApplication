package com.haulmont.testtask.model;


import org.hibernate.annotations.Check;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Orders")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID", nullable = false)
    private Long orderID;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ClientID",
            referencedColumnName = "ClientID",
            updatable = false,
            nullable = false)
    private Client clientID;

    @Column(name = "Description", nullable = false, length = 1000)
    private String description;

    @Column(name = "CreationDate", nullable = false)
    private Date creationDate;

    @Column(name = "CompletionDate")
    private Date completionDate;

    @Column(name = "TotalCost", nullable = false, precision=10, scale=2)
    private BigDecimal totalCost;

    @Column(name = "Currency", nullable = false, length = 10)
    private String currency;

    @Column(name = "Status", nullable = false, length = 20)
    @Check(constraints = "status IN ('Planned', 'Completed', 'Customer Accepted')")
    private String status;

    public Order() {
    }

    public Long getOrderID() {
        return orderID;
    }

    public Client getClientID() {
        return clientID;
    }

    public void setClientID(Client clientID) {
        this.clientID = clientID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order: "
                + this.orderID + ", "
                + this.clientID + ", "
                + this.description + ", "
                + this.creationDate + ", "
                + this.completionDate + ", "
                + this.totalCost + ", "
                + this.currency + ", "
                + this.status;
    }
}