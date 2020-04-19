package com.kiroule.vaadin.businessapp.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.kiroule.vaadin.businessapp.backend.BankAccount;
import com.kiroule.vaadin.businessapp.backend.DummyData;
import com.kiroule.vaadin.businessapp.ui.MainLayout;
import com.kiroule.vaadin.businessapp.ui.components.FlexBoxLayout;
import com.kiroule.vaadin.businessapp.ui.components.ListItem;
import com.kiroule.vaadin.businessapp.ui.components.navigation.bar.AppBar;
import com.kiroule.vaadin.businessapp.ui.layout.size.Horizontal;
import com.kiroule.vaadin.businessapp.ui.layout.size.Vertical;
import com.kiroule.vaadin.businessapp.ui.util.*;
import com.kiroule.vaadin.businessapp.ui.util.css.*;

import java.time.LocalDate;

@PageTitle("Account Details")
@Route(value = "account-details", layout = MainLayout.class)
public class AccountDetails extends ViewFrame implements HasUrlParameter<Long> {

	public int RECENT_TRANSACTIONS = 4;

	private ListItem availability;
	private ListItem bankAccount;
	private ListItem updated;

	private BankAccount account;

	@Override
	public void setParameter(BeforeEvent beforeEvent, Long id) {
		account = DummyData.getBankAccount(id);
		setViewContent(createContent());
	}

	private Component createContent() {
		FlexBoxLayout content = new FlexBoxLayout(
				createLogoSection(),
				createRecentTransactionsList(),
				createMonthlyOverviewChart()
		);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
		content.setMaxWidth("840px");
		return content;
	}

	private Section createLogoSection() {
		Image image = new Image(account.getLogoPath(), "Company logo");
		image.addClassName(LumoStyles.Margin.Horizontal.L);
		UIUtils.setBorderRadius(BorderRadius._50, image);
		image.setHeight("200px");
		image.setWidth("200px");

		availability = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR), "", "Availability");
		UIUtils.setFontSize(FontSize.XXL, availability.getPrimary());
		UIUtils.setFontWeight(FontWeight.BOLD, availability.getPrimary());
		availability.setDividerVisible(true);
		availability.setId("availability");
		availability.setReverse(true);

		bankAccount = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.INSTITUTION), "", "");
		bankAccount.setDividerVisible(true);
		bankAccount.setId("bankAccount");
		bankAccount.setReverse(true);
		bankAccount.setWhiteSpace(WhiteSpace.PRE_LINE);

		updated = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), "", "Updated");
		updated.setReverse(true);

		FlexBoxLayout listItems = new FlexBoxLayout(availability, bankAccount, updated);
		listItems.setFlexDirection(FlexDirection.COLUMN);

		Section section = new Section(image, listItems);
		section.addClassNames(
				BoxShadowBorders.BOTTOM,
				LumoStyles.Padding.Bottom.L
		);
		UIUtils.setAlignItems(AlignItems.CENTER, section);
		UIUtils.setDisplay(Display.FLEX, section);
		UIUtils.setFlexGrow(1, listItems);
		UIUtils.setFlexWrap(FlexWrap.WRAP, section);
		UIUtils.setJustifyContent(JustifyContent.CENTER, section);
		section.setWidthFull();
		return section;
	}

	private Section createRecentTransactionsList() {
		H2 title = new H2("Recent Transactions");

		Button viewAll = UIUtils.createSmallButton("View All");
		viewAll.addClickListener(e -> UIUtils.showNotification("Not implemented yet."));
		UIUtils.setFlexShrink(0, viewAll);

		Header header = new Header(title, viewAll);
		header.addClassName(LumoStyles.Margin.Responsive.Horizontal.L);
		UIUtils.setAlignItems(AlignItems.BASELINE, header);
		UIUtils.setDisplay(Display.FLEX, header);
		UIUtils.setJustifyContent(JustifyContent.SPACE_BETWEEN, header);

		Div items = new Div();
		items.addClassNames(BoxShadowBorders.BOTTOM, LumoStyles.Padding.Bottom.L);

		for (int i = 0; i < RECENT_TRANSACTIONS; i++) {
			Double amount = DummyData.getAmount();
			Label amountLabel = UIUtils.createAmountLabel(amount);
			if (amount > 0) {
				UIUtils.setTextColor(TextColor.SUCCESS, amountLabel);
			} else {
				UIUtils.setTextColor(TextColor.ERROR, amountLabel);
			}
			ListItem item = new ListItem(
					DummyData.getLogo(),
					DummyData.getCompany(),
					UIUtils.formatDate(LocalDate.now().minusDays(i)),
					amountLabel
			);
			// Dividers for all but the last item
			item.setDividerVisible(i < RECENT_TRANSACTIONS - 1);
			items.add(item);
		}

		return new Section(header, items);
	}

	private Section createMonthlyOverviewChart() {
		H2 title = new H2("Monthly Overview");
		Header header = new Header(title);
		header.addClassName(LumoStyles.Margin.Responsive.Horizontal.L);

		Chart chart = new Chart(ChartType.COLUMN);
		chart.setHeight("400px");

		Configuration conf = chart.getConfiguration();
		conf.setTitle("");
		conf.getLegend().setEnabled(true);

		XAxis xAxis = new XAxis();
		xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		conf.addxAxis(xAxis);

		conf.getyAxis().setTitle("Amount ($)");

		// Withdrawals and deposits
		ListSeries withDrawals = new ListSeries("Withdrawals");
		ListSeries deposits = new ListSeries("Deposits");

		for (int i = 0; i < 8; i++) {
			withDrawals.addData(DummyData.getRandomInt(5000, 10000));
			deposits.addData(DummyData.getRandomInt(5000, 10000));
		}

		conf.addSeries(withDrawals);
		conf.addSeries(deposits);

		return new Section(header, chart);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);

		initAppBar();
		UI.getCurrent().getPage().setTitle(account.getOwner());

		availability.setPrimaryText(UIUtils.formatAmount(account.getAvailability()));
		bankAccount.setPrimaryText(account.getAccount());
		bankAccount.setSecondaryText(account.getBank());
		updated.setPrimaryText(UIUtils.formatDate(account.getUpdated()));
	}

	private AppBar initAppBar() {
		AppBar appBar = MainLayout.get().getAppBar();
		appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
		appBar.getContextIcon().addClickListener(e -> UI.getCurrent().navigate(Accounts.class));
		appBar.setTitle(account.getOwner());
		return appBar;
	}
}
