package com.haulmont.testtask.ui;

import com.haulmont.testtask.service.ClientService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class DeleteClientDialog extends Window {

    private ClientService clientService = new ClientService();
    private ChangeHandler changeHandler = new ChangeHandler() {
        @Override
        public void onChange() {

        }
    };

    Button yes = new Button("Yes");
    Button no = new Button("No");

    public DeleteClientDialog(Long clientId) {

        center();

        setClosable(false);
        setResizable(false);
        setDraggable(false);

        Label titleLabel = new Label(
                "Are you sure you want to delete the item?");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        HorizontalLayout actions = new HorizontalLayout();
        actions.setSizeFull();
        actions.addComponents(yes, no);

        yes.addStyleNames(ValoTheme.BUTTON_HUGE, ValoTheme.BUTTON_DANGER);
        no.addStyleName(ValoTheme.BUTTON_HUGE);

        yes.addClickListener(e -> delete(clientId));
        no.addClickListener(e -> close());

        actions.setComponentAlignment(no, Alignment.TOP_LEFT);
        actions.setComponentAlignment(yes, Alignment.TOP_RIGHT);
        layout.addComponents(titleLabel, actions);
        layout.setComponentAlignment(titleLabel, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(actions, Alignment.MIDDLE_CENTER);

        setContent(layout);

        setModal(true);
        setHeight(25, Unit.PERCENTAGE);
        setWidth(25, Unit.PERCENTAGE);
    }

    public interface ChangeHandler {
        void onChange();
    }

    public void delete(Long clientId) {
            clientService.delete(clientId);
            close();
            changeHandler.onChange();
        }
}

