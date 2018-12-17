package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.service.ClientService;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.vaadin.addons.filteringgrid.FilterGrid;
import org.vaadin.addons.filteringgrid.filters.InMemoryFilter;

public class ClientsView extends Composite implements View {

    private ClientService clientService = new ClientService();
    private FilterGrid<Client> grid = new FilterGrid<>();
    private Button add = new Button("Add");
    private Button edit = new Button("Edit");
    private Button delete = new Button("Delete");

    public ClientsView() {

        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        final HorizontalLayout hl = new HorizontalLayout();
        hl.addComponents(add, edit, delete);

        FilterGrid.Column<Client, Long> colClientId = grid.addColumn(Client::getId).setCaption("ID")
                .setResizable(false);
        FilterGrid.Column<Client, String> colFirstName = grid.addColumn(Client::getFirstName).setCaption("FirstName")
                .setResizable(false);
        FilterGrid.Column<Client, String> colSurname = grid.addColumn(Client::getSurname).setCaption("Surname")
                .setResizable(false);
        FilterGrid.Column<Client, String> colMiddleName = grid.addColumn(Client::getMiddleName).setCaption("MiddleName")
                .setResizable(false);
        FilterGrid.Column<Client, String> colPhoneNumber = grid.addColumn(Client::getPhoneNumber).setCaption("PhoneNumber")
                .setResizable(false);

        grid.setSizeFull();
        grid.setSelectionMode(FilterGrid.SelectionMode.SINGLE);
        grid.setItems(clientService.findAll());
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeight(185, Unit.PERCENTAGE);


        // --- Filters ---
        TextField clientIdFilter = new TextField();
        TextField firstNameFilter = new TextField();
        TextField surnameFilter = new TextField();
        TextField middleNameFilter = new TextField();
        TextField phoneNumberFilter = new TextField();

        clientIdFilter.setPlaceholder("Filter by ID...");
        clientIdFilter.setHeight(80, Unit.PERCENTAGE);
        clientIdFilter.setWidth(100, Unit.PERCENTAGE);

        firstNameFilter.setPlaceholder("Filter by Name...");
        firstNameFilter.setHeight(80, Unit.PERCENTAGE);
        firstNameFilter.setWidth(100, Unit.PERCENTAGE);

        surnameFilter.setPlaceholder("Filter by Surname...");
        surnameFilter.setHeight(80, Unit.PERCENTAGE);
        surnameFilter.setWidth(100, Unit.PERCENTAGE);

        middleNameFilter.setPlaceholder("Filter by Middle Name...");
        middleNameFilter.setHeight(80, Unit.PERCENTAGE);
        middleNameFilter.setWidth(100, Unit.PERCENTAGE);

        phoneNumberFilter.setPlaceholder("Filter by Phone Number...");
        phoneNumberFilter.setHeight(80, Unit.PERCENTAGE);
        phoneNumberFilter.setWidth(100, Unit.PERCENTAGE);

        colClientId.setFilter(
                clientIdFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colFirstName.setFilter(
                firstNameFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colSurname.setFilter(
                surnameFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colMiddleName.setFilter(
                middleNameFilter, InMemoryFilter.StringComparator.containsIgnoreCase());
        colPhoneNumber.setFilter(
                phoneNumberFilter, InMemoryFilter.StringComparator.containsIgnoreCase());

        //Buttons
        delete.setEnabled(false);
        edit.setEnabled(false);
        grid.addSelectionListener(event -> {
            //The buttons are active only in case of selected item in the table
            delete.setEnabled(true);
            edit.setEnabled(true);
        });

        add.addClickListener(event -> {
            AddClientModalView sub = new AddClientModalView();
            // Add it to the root component
            UI.getCurrent().addWindow(sub);
            sub.addCloseListener(e -> refresh());
        });

        edit.addClickListener(event -> {
            Client client = grid.getSelectedItems().iterator().next();
            EditClientModalView editView = new EditClientModalView(client);
            UI.getCurrent().addWindow(editView);
            editView.addCloseListener(e -> refresh());
        });

        delete.addClickListener(event -> {
            Long clientId = grid.getSelectedItems().iterator().next().getId();
            DeleteClientDialog deleteDialog = new DeleteClientDialog(clientId);
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
        grid.setItems(clientService.findAll());
        delete.setEnabled(false);
        edit.setEnabled(false);
    }
}
