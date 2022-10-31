package com.example.application.views.list;

import com.example.application.data.security.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class MainLayout extends AppLayout {

    private SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink list = new RouterLink("List", ListView.class);
        list.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(list, new RouterLink("Dashboard", DashboardView.class)));

//        addToDrawer(new VerticalLayout(list, new DashboardEntity()));

    }

    private void createHeader() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Logout", e -> securityService.logout());
        Button user = new Button("My name", e-> Notification.show(securityService.getUserName()));
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, user, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
}
