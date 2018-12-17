package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Order;
import com.haulmont.testtask.service.ClientService;
import com.haulmont.testtask.service.OrderService;
import com.haulmont.testtask.utils.SqlDateToLocalDateConverter;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.math.BigDecimal;
import java.util.Calendar;

public class EditOrderModalView extends Window {

    private OrderService orderService = new OrderService();
    private ClientService clientService = new ClientService();
    private Order order = new Order();

    private static final String CUSTOMER_ACCEPTED = "Customer Accepted";
    private static final String COMPLETED = "Completed";
    private static final String PLANNED = "Planned";

    private static final String CURRENCY_SYMBOL_DOLLAR = "$";
    private static final String CURRENCY_SYMBOL_EURO = "€";
    private static final String CURRENCY_SYMBOL_POUND = "£";
    private static final String CURRENCY_SYMBOL_ROUBLE = "\u20BD";


    Binder<Order> binder = new Binder<>(Order.class);
    private AddOrderModalView.ChangeHandler changeHandler = new AddOrderModalView.ChangeHandler() {
        @Override
        public void onChange() {

        }
    };

    private FormLayout formLayout = new FormLayout();
    private ComboBox<Client> clientId = new ComboBox("Client ID");
    private TextField description = new TextField("Description");
    private DateField creationDate = new DateField("Creation Date");
    private DateField completionDate = new DateField("Completion Date");
    private TextField totalCost = new TextField("Total Cost");
    private ComboBox<String> currency = new ComboBox<>("Currency");
    private ComboBox<String> status = new ComboBox<>("Status");
    private Button update = new Button("Update");
    private Button cancel = new Button("Cancel");


    public EditOrderModalView(Order order) {
        super("Edit an order");

        center();

        setClosable(false);
        setResizable(false);
        setDraggable(false);

        VerticalLayout subContent = new VerticalLayout();
        subContent.setSizeFull();

        HorizontalLayout actions = new HorizontalLayout();
        actions.addComponents(update, cancel);

        creationDate.setDateFormat("yyyy-MM-dd HH:mm:ss");
        completionDate.setDateFormat("yyyy-MM-dd HH:mm:ss");

        formLayout.addComponents(clientId, description, creationDate,
                completionDate, totalCost, currency,
                status, actions);

        subContent.addComponent(formLayout);
        setContent(subContent);

        update.addStyleNames(ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_PRIMARY);
        cancel.addStyleName(ValoTheme.BUTTON_SMALL);

        clientId.setItems(clientService.findAll()); // list/set of possible clients.

        // Using clients name for item captions now, but you can also use id or both
        clientId.setItemCaptionGenerator(Client::getFirstName);

        currency.setItems(CURRENCY_SYMBOL_DOLLAR, CURRENCY_SYMBOL_EURO, CURRENCY_SYMBOL_POUND, CURRENCY_SYMBOL_ROUBLE);
        status.setItems(PLANNED, CUSTOMER_ACCEPTED, COMPLETED);
        update.addClickListener(e -> update(order));
        cancel.addClickListener(e -> close());

        bindingFields();

        setModal(true);
        setHeight(70, Unit.PERCENTAGE);
        setWidth(30, Unit.PERCENTAGE);
    }

    private void bindingFields() {
        binder.forField(this.clientId)
                .asRequired()
                .bind(Order::getClientID, Order::setClientID);
        binder.forField(this.description)
                .asRequired()
                .withValidator(new StringLengthValidator(
                        "Please add description. The maximum length is 1000 characters", 1, 1000))
                .bind(Order::getDescription, Order::setDescription);
        binder.forField(this.creationDate)
                .asRequired()
                .withConverter(new SqlDateToLocalDateConverter())
                .bind(Order::getCreationDate, Order::setCreationDate);
        binder.forField(this.completionDate)
                .withConverter(new SqlDateToLocalDateConverter())
                .bind(Order::getCompletionDate, Order::setCompletionDate);
        binder.forField(this.totalCost)
                .asRequired()
                .withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter(new BigDecimal(10.00), "Price must be in ##.## format"))
                .bind(Order::getTotalCost, Order::setTotalCost);
        binder.forField(this.currency)
                .asRequired()
                .bind(Order::getCurrency, Order::setCurrency);
        binder.forField(this.status)
                .asRequired()
                .bind(Order::getStatus, Order::setStatus);

        order.setCreationDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        binder.setBean(order);
    }

    public interface ChangeHandler {
        void onChange();
    }

    public void update(Order order) {
        if (binder.validate().isOk()) {
            orderService.update(order);
            close();
            changeHandler.onChange();
        }
    }
}
