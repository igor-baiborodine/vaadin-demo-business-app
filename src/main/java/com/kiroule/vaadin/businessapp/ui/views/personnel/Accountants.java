package com.kiroule.vaadin.businessapp.ui.views.personnel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditorPosition;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.kiroule.vaadin.businessapp.backend.DummyData;
import com.kiroule.vaadin.businessapp.backend.Person;
import com.kiroule.vaadin.businessapp.ui.MainLayout;
import com.kiroule.vaadin.businessapp.ui.components.FlexBoxLayout;
import com.kiroule.vaadin.businessapp.ui.components.Initials;
import com.kiroule.vaadin.businessapp.ui.components.ListItem;
import com.kiroule.vaadin.businessapp.ui.layout.size.Horizontal;
import com.kiroule.vaadin.businessapp.ui.layout.size.Right;
import com.kiroule.vaadin.businessapp.ui.layout.size.Top;
import com.kiroule.vaadin.businessapp.ui.layout.size.Vertical;
import com.kiroule.vaadin.businessapp.ui.util.LumoStyles;
import com.kiroule.vaadin.businessapp.ui.util.UIUtils;
import com.kiroule.vaadin.businessapp.ui.util.css.BoxSizing;
import com.kiroule.vaadin.businessapp.ui.views.ViewFrame;

@Route(value = "accountants", layout = MainLayout.class)
@PageTitle("Accountants")
public class Accountants extends ViewFrame {

	private Grid<Person> grid;
	private ListDataProvider<Person> dataProvider;

	public Accountants() {
		setViewContent(createContent());
		filter();
	}

	private Component createContent() {
		FlexBoxLayout content = new FlexBoxLayout(createCrud());
		content.setBoxSizing(BoxSizing.BORDER_BOX);
		content.setHeightFull();
		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
		return content;
	}

	private Crud<Person> createCrud() {
		Crud<Person> crud = new Crud<>(Person.class, createGrid(), createEditor());
		UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, crud);
		crud.setEditOnClick(true);
		crud.setEditorPosition(CrudEditorPosition.BOTTOM);
		crud.setSizeFull();
		return crud;
	}

	private Grid<Person> createGrid() {
		grid = new Grid<>();
		grid.setSelectionMode(SelectionMode.SINGLE);

		dataProvider = DataProvider.ofCollection(DummyData.getPersons());
		grid.setDataProvider(dataProvider);
		grid.setHeightFull();

		grid.addColumn(Person::getId)
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("ID")
				.setSortable(true);
		grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
				.setAutoWidth(true)
				.setHeader("Name");
		grid.addColumn(new ComponentRenderer<>(this::createActive))
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Active")
				.setTextAlign(ColumnTextAlign.END);
		grid.addColumn(new ComponentRenderer<>(this::createInvoices))
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Invoices")
				.setTextAlign(ColumnTextAlign.END);
		grid.addColumn(new ComponentRenderer<>(this::createCompanies))
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Companies")
				.setTextAlign(ColumnTextAlign.END);
		grid.addColumn(new ComponentRenderer<>(this::createDate)).setFlexGrow(0)
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Last Report")
				.setTextAlign(ColumnTextAlign.END);

		return grid;
	}

	private Component createUserInfo(Person person) {
		ListItem item = new ListItem(
				new Initials(person.getInitials()), person.getName(),
				person.getEmail());
		item.setPadding(Vertical.XS);
		item.setSpacing(Right.M);
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

	private Component createInvoices() {
		return UIUtils.createAmountLabel(DummyData.getRandomInt(0, 5000));
	}

	private Component createCompanies() {
		return UIUtils.createUnitsLabel(DummyData.getRandomInt(0, 50));
	}

	private Component createDate(Person person) {
		return new Span(UIUtils.formatDate(person.getLastModified()));
	}

	private BinderCrudEditor<Person> createEditor() {
		Binder<Person> binder = new Binder<>(Person.class);

		TextField firstName = new TextField();
		firstName.setWidthFull();
		binder.bind(firstName, "firstName");

		TextField lastName = new TextField();
		lastName.setWidthFull();
		binder.bind(lastName, "lastName");

		RadioButtonGroup<String> status = new RadioButtonGroup<>();
		status.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		status.setItems("Active", "Inactive");
		binder.bind(status, 
			(person) -> person.getRandomBoolean() ? "Active" : "Inactive", 
			(person, value) -> person.setRandomBoolean(value == "Active" ? true : false)
		);

		FlexLayout phone = UIUtils.createPhoneLayout();

		TextField email = new TextField();
		email.setWidthFull();
		binder.bind(email, "email");

		ComboBox<String> company = new ComboBox<>();
		company.setItems(DummyData.getCompanies());
		company.setValue(DummyData.getCompany());
		company.setWidthFull();

		// Form layout
		FormLayout form = new FormLayout();
		form.addClassNames(
				LumoStyles.Padding.Bottom.L,
				LumoStyles.Padding.Horizontal.L,
				LumoStyles.Padding.Top.S
		);
		form.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1,
						FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("21em", 2,
						FormLayout.ResponsiveStep.LabelsPosition.TOP)
		);
		form.addFormItem(firstName, "First Name");
		form.addFormItem(lastName, "Last Name");
		FormLayout.FormItem statusItem = form.addFormItem(status, "Status");
		FormLayout.FormItem phoneItem = form.addFormItem(phone, "Phone");
		FormLayout.FormItem emailItem = form.addFormItem(email, "Email");
		FormLayout.FormItem companyItem = form.addFormItem(company, "Company");
		FormLayout.FormItem uploadItem = form.addFormItem(new Upload(), "Image");
		UIUtils.setColSpan(2, statusItem, phoneItem, emailItem, companyItem, uploadItem);
		return new BinderCrudEditor<>(binder, form);
	}

	private void filter() {
		dataProvider.setFilterByValue(Person::getRole, Person.Role.ACCOUNTANT);
	}

}
