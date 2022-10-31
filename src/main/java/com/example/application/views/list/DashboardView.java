package com.example.application.views.list;

import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Vaadin CRM")
@PermitAll
public class DashboardView extends VerticalLayout {

    private CrmService service;

    public DashboardView(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getContactStats(), getCompanyStats());
    }

    private Component getContactStats() {
        Span stats = new Span(service.countContacts() + " contacts");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getCompanyStats() {
//        Chart chart = new Chart(ChartType.PIE);
//        DataSeries dataSeries = new DataSeries();
//        service.findAllCompany().forEach(company -> dataSeries.add(new DataSeriesItem(company.getName(), company.getEmployees().size())));
//
//        chart.getConfiguration().setSeries(dataSeries);
//        return chart;

        return new VerticalLayout();

    }
}
