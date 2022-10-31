package com.example.application.views.list;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

import javax.annotation.security.RolesAllowed;

@RolesAllowed("ADMIN")
public class DashboardEntity extends Span {

    public DashboardEntity() {

        add(new RouterLink("Dashboard", DashboardView.class));
    }
}
