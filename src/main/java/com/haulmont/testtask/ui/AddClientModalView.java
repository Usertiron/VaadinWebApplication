
package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.service.ClientService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class AddClientModalView extends Window {

    private static final String ENTER_VALID_PHONE_NUMBER = "Please add phone number in international format";
    private static final String INTERNATIONAL_PHONE_FORMAT = "\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|\n" +
            "2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|\n" +
            "4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$";

    private ClientService clientService = new ClientService();
    private Client client = new Client();

    Binder<Client> binder = new Binder<>(Client.class);
    private ChangeHandler changeHandler = new ChangeHandler() {
        @Override
        public void onChange() {

        }
    };

    private FormLayout formLayout = new FormLayout();
    private TextField firstName = new TextField("First name");
    private TextField surname = new TextField("Surname");
    private TextField middleName = new TextField("Middle Name");
    private TextField phoneNumber = new TextField("Phone number");
    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    public AddClientModalView() {
        super("Add a new client");

        center();

        setClosable(false);
        setResizable(false);
        setDraggable(false);

        VerticalLayout subContent = new VerticalLayout();
        subContent.setSizeFull();

        HorizontalLayout actions = new HorizontalLayout();
        actions.addComponents(save, cancel);

        formLayout.addComponents(firstName, surname, middleName,
                                 phoneNumber, actions);

        subContent.addComponent(formLayout);
        setContent(subContent);

        save.addStyleNames(ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_PRIMARY);
        cancel.addStyleName(ValoTheme.BUTTON_SMALL);

        save.addClickListener(e -> save());
        cancel.addClickListener(e -> close());

        bindingFields();

        setModal(true);
        setHeight(50, Unit.PERCENTAGE);
        setWidth(30, Unit.PERCENTAGE);
    }

    private void bindingFields() {
        binder.forField(this.firstName)
                .asRequired()
                .withValidator(new StringLengthValidator(
                        "Please add the first name. The name length should not exceed 20 characters", 1, 20))
                .bind(Client::getFirstName, Client::setFirstName);
        binder.forField(this.surname)
                .asRequired()
                .withValidator(new StringLengthValidator(
                        "Please add surname. The surname length should not exceed 20 characters", 1, 20))
                .bind(Client::getSurname, Client::setSurname);
        binder.forField(this.middleName)
                .bind(Client::getMiddleName, Client::setMiddleName);
        binder.forField(this.phoneNumber)
                .asRequired()
                .withValidator(new RegexpValidator(ENTER_VALID_PHONE_NUMBER, INTERNATIONAL_PHONE_FORMAT))
                .bind(Client::getPhoneNumber, Client::setPhoneNumber);

        binder.setBean(client);
    }

    public interface ChangeHandler {
        void onChange();
    }

    private void save() {
        if (binder.validate().isOk()) {
            clientService.persist(client);
            close();
            changeHandler.onChange();
        }
    }
}