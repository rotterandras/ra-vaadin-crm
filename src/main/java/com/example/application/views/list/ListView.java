package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.Collections;

@Route(value = "", layout = MainLayout.class)
@CssImport(value = "./themes/flowcrmtutorial/styles.css", themeFor = "vaadin-grid")
@RolesAllowed("USER")
public class ListView extends VerticalLayout implements HasDynamicTitle {

    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private final TextField filter = new TextField();
    private ContactForm form;
    private CrmService service;

    public ListView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        initForm();
        initGrid();

        add(
                getToolbar(),
                getContent()
        );


    }

    private Component getContent() {
        HorizontalLayout contentLayout = new HorizontalLayout(grid, form);
        contentLayout.setFlexGrow(2, grid);
        contentLayout.setFlexGrow(1, form);
        contentLayout.addClassName("content-layout");
        contentLayout.setSizeFull();

        return contentLayout;
    }

    private void initForm() {
        form = new ContactForm(service.findAllCompany(), service.findAllStatus());
        form.setWidth("25em");

        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());

    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void saveContact(ContactForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filter.setPlaceholder("Filter by name");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());
        filter.setWidth("20%");

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(e -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filter, addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    private void initGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");

        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));

        grid.getColumnByKey("firstName").setClassNameGenerator(name -> {
            if (name.getFirstName().equals("Barry")) {
                return "grid-bold";
            }
            return "";
        });

//        grid.addItemClickListener(e -> editContact(e.getItem()));
        updateList();

        closeEditor();
    }

    private void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private GridListDataView<Contact> updateList() {
        return grid.setItems(service.findAllContacts(filter.getValue()));
    }

    @Override
    public String getPageTitle() {
        return "Contacts | Vaadin CRM";
    }
}
