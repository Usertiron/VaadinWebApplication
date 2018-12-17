package com.haulmont.testtask.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@PushStateNavigation
public class VaadinUI extends UI {

    private static final String MENU_TITLE = "Menu";
    private static final String CLIENTS_MENU_ITEM = "Clients";
    private static final String ORDERS_MENU_ITEM = "Orders";
    private static final String CLIENTS_VIEW = "clients";
    private static final String ORDERS_VIEW = "orders";

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Page.getCurrent().setTitle("Vaadin Example");

        Label title = new Label(MENU_TITLE);
        title.addStyleName(ValoTheme.MENU_TITLE);

        Button clients = new Button(CLIENTS_MENU_ITEM, e -> getNavigator().navigateTo(CLIENTS_VIEW));
        Button orders = new Button(ORDERS_MENU_ITEM, e -> getNavigator().navigateTo(ORDERS_VIEW));
        clients.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        orders.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        CssLayout menu = new CssLayout(title, clients, orders);
        menu.addStyleName(ValoTheme.MENU_ROOT);

        CssLayout viewContainer = new CssLayout();
        viewContainer.setSizeFull();

        HorizontalLayout mainLayout = new HorizontalLayout(menu, viewContainer);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(viewContainer, 20);
        setContent(mainLayout);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addView("", DefaultView.class);
        navigator.addView(CLIENTS_VIEW, ClientsView.class);
        navigator.addView(ORDERS_VIEW, OrdersView.class);
    }
}