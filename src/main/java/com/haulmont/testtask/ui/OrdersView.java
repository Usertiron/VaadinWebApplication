package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Order;
import com.haulmont.testtask.service.OrderService;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.vaadin.addons.filteringgrid.FilterGrid;
import org.vaadin.addons.filteringgrid.filters.InMemoryFilter;

import java.math.BigDecimal;
import java.sql.Date;

public class OrdersView extends Composite implements View {

    private OrderService orderService = new OrderService();
    private FilterGrid<Order> grid = new FilterGrid<>();
    private Button add = new Button("Add");
    private Button edit = new Button("Edit");
    private Button delete = new Button("Delete");

    private static final String PLANNED = "Planned";
    private static final String CUSTOMER_ACCEPTED = "Customer Accepted";
    private static final String COMPLETED = "Completed";

    public OrdersView() {

        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        final HorizontalLayout hl = new HorizontalLayout();
        hl.addComponents(add, edit, delete);

        FilterGrid.Column<Order, Long> colOrderID = grid.addColumn(Order::getOrderID)
                .setCaption("ID")
                .setResizable(false);
        FilterGrid.Column<Order, Client> colClientID = grid.addColumn(Order::getClientID)
                .setCaption("Client ID")
                .setResizable(false);
        FilterGrid.Column<Order, String> colDescription = grid.addColumn(Order::getDescription)
                .setCaption("Description")
                .setResizable(false);
        FilterGrid.Column<Order, Date> colCreationDate = grid.addColumn(Order::getCreationDate)
                .setCaption("Creation Date")
                .setResizable(false);
        FilterGrid.Column<Order, Date> colCompletionDate = grid.addColumn(Order::getCompletionDate)
                .setCaption("Completion Date")
                .setResizable(false);
        FilterGrid.Column<Order, BigDecimal> colTotalCost = grid.addColumn(Order::getTotalCost)
                .setCaption("Total Cost")
                .setResizable(false);
        FilterGrid.Column<Order, String> colCurrency = grid.addColumn(Order::getCurrency)
                .setCaption("Currency")
                .setResizable(false);
        FilterGrid.Column<Order, String> colStatus = grid.addColumn(Order::getStatus)
                .setCaption("Status")
                .setResizable(false);

        grid.setSizeFull();
        grid.setSelectionMode(FilterGrid.SelectionMode.SINGLE);
        grid.setItems(orderService.findAll());
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeight(185, Unit.PERCENTAGE);

        // --- Filters ---
        TextField orderIdFilter = new TextField();
        TextField clientIdFilter = new TextField();
        TextField descriptonFilter = new TextField();
        TextField creationDateFilter = new TextField();
        TextField completionDateFilter = new TextField();

        TextField totalCostFilter = new TextField();
        TextField currencyFilter = new TextField();
        ComboBox statusFilter = new ComboBox();

        orderIdFilter.setPlaceholder("Filter by Order ID...");
        orderIdFilter.setHeight(80, Unit.PERCENTAGE);
        orderIdFilter.setWidth(100, Unit.PERCENTAGE);

        clientIdFilter.setPlaceholder("Filter by Client ID...");
        clientIdFilter.setHeight(80, Unit.PERCENTAGE);
        clientIdFilter.setWidth(100, Unit.PERCENTAGE);

        descriptonFilter.setPlaceholder("Filter by Description...");
        descriptonFilter.setHeight(80, Unit.PERCENTAGE);
        descriptonFilter.setWidth(100, Unit.PERCENTAGE);

        creationDateFilter.setPlaceholder("Filter by Creation Date...");
        creationDateFilter.setHeight(80, Unit.PERCENTAGE);
        creationDateFilter.setWidth(100, Unit.PERCENTAGE);

        completionDateFilter.setPlaceholder("Filter by Completion Date...");
        completionDateFilter.setHeight(80, Unit.PERCENTAGE);
        completionDateFilter.setWidth(100, Unit.PERCENTAGE);

        totalCostFilter.setPlaceholder("Filter by Cost...");
        totalCostFilter.setHeight(80, Unit.PERCENTAGE);
        totalCostFilter.setWidth(100, Unit.PERCENTAGE);

        currencyFilter.setPlaceholder("Filter by Currency...");
        currencyFilter.setHeight(80, Unit.PERCENTAGE);
        currencyFilter.setWidth(100, Unit.PERCENTAGE);

        statusFilter.setHeight(80, Unit.PERCENTAGE);
        statusFilter.setWidth(100, Unit.PERCENTAGE);

        statusFilter.setItems(PLANNED, CUSTOMER_ACCEPTED, COMPLETED);

        colOrderID.setFilter(
                orderIdFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colClientID.setFilter(
                clientIdFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colDescription.setFilter(
                descriptonFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colCreationDate.setFilter(
                creationDateFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colCompletionDate.setFilter(
                completionDateFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colTotalCost.setFilter(
                totalCostFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colCurrency.setFilter(
                currencyFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colStatus.setFilter(
                statusFilter, InMemoryFilter.StringComparator.containsIgnoreCase());

        //Buttons
        delete.setEnabled(false);
        edit.setEnabled(false);
        grid.addSelectionListener(event -> {
            //The buttons are active in case of selected item in the table
            delete.setEnabled(true);
            edit.setEnabled(true);
        });

        add.addClickListener(event -> {
             AddOrderModalView sub = new AddOrderModalView();
            // Add it to the root component
             UI.getCurrent().addWindow(sub);
             sub.addCloseListener(e -> refresh());
        });

        edit.addClickListener(event -> {
             Order order = grid.getSelectedItems().iterator().next();
             EditOrderModalView editView = new EditOrderModalView(order);
             UI.getCurrent().addWindow(editView);
             editView.addCloseListener(e -> refresh());
        });

        delete.addClickListener(event -> {
             Long orderId = grid.getSelectedItems().iterator().next().getOrderID();
             DeleteOrderDialog deleteDialog = new DeleteOrderDialog(orderId);
             UI.getCurrent().addWindow(deleteDialog);
             deleteDialog.addCloseListener(e -> refresh());
        });

        //Add all components to layout
        layout.addComponent(grid);
        layout.addComponent(hl);
        layout.setComponentAlignment(hl, Alignment.BOTTOM_LEFT);
        setCompositionRoot(layout);
    }

    private void refresh() {
        grid.setItems(orderService.findAll());
        delete.setEnabled(false);
        edit.setEnabled(false);
    }
}
