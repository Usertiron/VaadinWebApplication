# Vaadin Web Application

Used technologies and frameworks:
- Java
- HSQLDB (standalone mode)
- Hibernate
- Vaadin Framework 8
- Jetty

## Description

This is Vaadin Web Application example. Vaadin is an open-source platform for web application development. The Vaadin platform includes a set of web components, a Java web framework, and a set of tools and application starters. Its flagship product, Vaadin Flow (previously Vaadin Framework) allows the implementation of HTML5 web user interfaces using the Java Programming Language.
In the Example, a Car Repair Shop presentation was developed with two related tables: clients and orders (1: M).

## UI and Operations

Every grid supports typical CRUD operations - create, read, update and delete. It is also possible to filter each column by value.
Below are a few screenshots of the web application GUI.

![Clients View](https://github.com/Usertiron/VaadinWebApplication/blob/master/screenshots/clients.png)

![Add a client](https://github.com/Usertiron/VaadinWebApplication/blob/master/screenshots/add.PNG)

![Add an order](https://github.com/Usertiron/VaadinWebApplication/blob/master/screenshots/add_ord.PNG)

![Edit a client](https://github.com/Usertiron/VaadinWebApplication/blob/master/screenshots/edit.PNG)

![Edit an order](https://github.com/Usertiron/VaadinWebApplication/blob/master/screenshots/edit_order.PNG)

![Delete](https://github.com/Usertiron/VaadinWebApplication/blob/master/screenshots/del.PNG)

## How to run

In order to run jetty server execute the following maven command  
```mvn jetty:run```

Open a browser in http://localhost:8080
