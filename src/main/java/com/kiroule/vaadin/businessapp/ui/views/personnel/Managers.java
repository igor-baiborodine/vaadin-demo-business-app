package com.kiroule.vaadin.businessapp.ui.views.personnel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.kiroule.vaadin.businessapp.backend.DummyData;
import com.kiroule.vaadin.businessapp.backend.Person;
import com.kiroule.vaadin.businessapp.ui.MainLayout;
import com.kiroule.vaadin.businessapp.ui.components.ListItem;
import com.kiroule.vaadin.businessapp.ui.components.detailsdrawer.DetailsDrawer;
import com.kiroule.vaadin.businessapp.ui.components.detailsdrawer.DetailsDrawerFooter;
import com.kiroule.vaadin.businessapp.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.kiroule.vaadin.businessapp.ui.util.LumoStyles;
import com.kiroule.vaadin.businessapp.ui.util.UIUtils;
import com.kiroule.vaadin.businessapp.ui.views.SplitViewFrame;

@Route(value = "managers", layout = MainLayout.class)
@PageTitle("Managers")
public class Managers extends SplitViewFrame {

    private Grid<Person> grid;
    private ListDataProvider<Person> dataProvider;

    private DetailsDrawer detailsDrawer;
    private Label detailsDrawerHeader;

    public Managers() {
        setViewContent(createContent());
        setViewDetails(createDetailsDrawer());
        setViewDetailsPosition(Position.BOTTOM);

        filter();
    }

    private Component createContent() {
        Div content = new Div(createGrid());
        content.addClassName("grid-view");
        return content;
    }

    private Grid createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(this::showDetails));
        dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);
        grid.setSizeFull();

        grid.addColumn(Person::getId).setHeader("ID").setFrozen(true)
                .setSortable(true).setWidth(UIUtils.COLUMN_WIDTH_XS)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setHeader("Name").setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createActive))
                .setHeader("Active").setWidth(UIUtils.COLUMN_WIDTH_XS)
                .setFlexGrow(0).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new ComponentRenderer<>(this::createApprovalLimit))
                .setHeader("Approval Limit ($)")
                .setWidth(UIUtils.COLUMN_WIDTH_L).setFlexGrow(0)
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new ComponentRenderer<>(this::createDate))
                .setHeader("Last Report").setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0).setTextAlign(ColumnTextAlign.END);

        return grid;
    }

    private Component createUserInfo(Person person) {
        ListItem item = new ListItem(
                UIUtils.createInitials(person.getInitials()), person.getName(),
                person.getEmail());
        item.setHorizontalPadding(false);
        return item;
    }

    private Component createActive(Person person) {
        Icon icon;
        if (person.getRandomBoolean()) {
            icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
        } else {
            icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
        }
        return icon;
    }

    private Component createApprovalLimit(Person person) {
        int amount = person.getRandomInteger() > 0 ? person.getRandomInteger()
                : 0;
        return UIUtils.createAmountLabel(amount);
    }

    private Component createDate(Person person) {
        return new Span(UIUtils.formatDate(person.getLastModified()));
    }

    private DetailsDrawer createDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.BOTTOM);

        // Header
        detailsDrawerHeader = new DetailsDrawerHeader("");
        detailsDrawer.setHeader(detailsDrawerHeader);

        // Footer
        DetailsDrawerFooter footer = new DetailsDrawerFooter();
        footer.addCancelListener(e -> detailsDrawer.hide());
        detailsDrawer.setFooter(footer);

        return detailsDrawer;
    }

    private void showDetails(Person person) {
        detailsDrawerHeader.setText(person.getName());
        detailsDrawer.setContent(createDetails(person));
        detailsDrawer.show();
    }

    private FormLayout createDetails(Person person) {
        TextField firstName = new TextField();
        firstName.setValue(person.getFirstName());
        firstName.setWidth("100%");

        TextField lastName = new TextField();
        lastName.setValue(person.getLastName());
        lastName.setWidth("100%");

        RadioButtonGroup<String> gender = new RadioButtonGroup<>();
        gender.setItems("Active", "Inactive");
        gender.setValue(person.getRandomBoolean() ? "Active" : "Inactive");

        FlexLayout phone = UIUtils.createPhoneLayout();

        TextField email = new TextField();
        email.setValue(person.getEmail());
        email.setWidth("100%");

        ComboBox company = new ComboBox();
        company.setItems(DummyData.getCompanies());
        company.setValue(DummyData.getCompany());
        company.setWidth("100%");

        // Form layout
        FormLayout form = new FormLayout();
        form.addClassNames(LumoStyles.Padding.Bottom.L,
                LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 2,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("1024px", 3,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));
        form.addFormItem(firstName, "First Name");
        form.addFormItem(lastName, "Last Name");
        form.addFormItem(gender, "Status");
        form.addFormItem(phone, "Phone");
        form.addFormItem(email, "Email");
        form.addFormItem(company, "Company");
        form.addFormItem(new Upload(), "Image");
        return form;
    }

    private void filter() {
        dataProvider.setFilterByValue(Person::getRole, Person.Role.MANAGER);
    }
}
