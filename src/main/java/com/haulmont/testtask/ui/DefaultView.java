package com.haulmont.testtask.ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.*;

public class DefaultView extends VerticalLayout implements View {

    public DefaultView() {
        Notification.show("Welcome to VAADIN Web Application");
    }

}
