package com.haulmont.testtask.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClientID", updatable = false, nullable = false)
    private Long clientID;

    @Column(name = "FirstName", nullable = false, length = 20)
    private String firstName;

    @Column(name = "Surname", nullable = false, length = 20)
    private String surname;

    @Column(name = "MiddleName", length = 20)
    private String middleName;

    @Column(name = "PhoneNumber", nullable = false)
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientID")
    private Set<Order> orders;

    public Client() {
    }

    public Long getId() {
        return clientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(Long clientID) {
        this.clientID = clientID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String toString() {
        return "" + this.clientID;
    }
}