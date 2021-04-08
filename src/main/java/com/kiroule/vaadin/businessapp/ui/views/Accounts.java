package com.kiroule.vaadin.businessapp.ui.views;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.kiroule.vaadin.businessapp.backend.BankAccount;
import com.kiroule.vaadin.businessapp.backend.DummyData;
import com.kiroule.vaadin.businessapp.ui.MainLayout;
import com.kiroule.vaadin.businessapp.ui.components.Badge;
import com.kiroule.vaadin.businessapp.ui.components.FlexBoxLayout;
import com.kiroule.vaadin.businessapp.ui.components.ListItem;
import com.kiroule.vaadin.businessapp.ui.layout.size.Horizontal;
import com.kiroule.vaadin.businessapp.ui.layout.size.Right;
import com.kiroule.vaadin.businessapp.ui.layout.size.Top;
import com.kiroule.vaadin.businessapp.ui.layout.size.Vertical;
import com.kiroule.vaadin.businessapp.ui.util.FontSize;
import com.kiroule.vaadin.businessapp.ui.util.LineHeight;
import com.kiroule.vaadin.businessapp.ui.util.LumoStyles;
import com.kiroule.vaadin.businessapp.ui.util.TextColor;
import com.kiroule.vaadin.businessapp.ui.util.UIUtils;
import com.kiroule.vaadin.businessapp.ui.util.css.BoxSizing;
import com.kiroule.vaadin.businessapp.ui.util.css.Overflow;
import com.kiroule.vaadin.businessapp.ui.util.css.PointerEvents;
import com.kiroule.vaadin.businessapp.ui.util.css.TextOverflow;
import com.kiroule.vaadin.businessapp.ui.util.css.lumo.BadgeColor;
import com.kiroule.vaadin.businessapp.ui.util.css.lumo.BadgeShape;
import com.kiroule.vaadin.businessapp.ui.util.css.lumo.BadgeSize;

@PageTitle("Accounts")
@Route(value = "accounts", layout = MainLayout.class)
public class Accounts extends ViewFrame {

	public static final int MOBILE_BREAKPOINT = 480;
	private Grid<BankAccount> grid;
	private Registration resizeListener;

	public Accounts() {
		setViewContent(createContent());
	}

	private Component createContent() {
		FlexBoxLayout content = new FlexBoxLayout(createGrid());
		content.setBoxSizing(BoxSizing.BORDER_BOX);
		content.setHeightFull();
		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
		return content;
	}

	private Grid createGrid() {
		grid = new Grid<>();
		grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));
		grid.addThemeName("mobile");
		grid.setDataProvider(DataProvider.ofCollection(DummyData.getBankAccounts()));
		grid.setId("accounts");
		grid.setSizeFull();

		// "Mobile" column
		grid.addColumn(new ComponentRenderer<>(this::getMobileTemplate))
				.setVisible(false);

		// "Desktop" columns
		grid.addColumn(BankAccount::getId)
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("ID")
				.setSortable(true);
		grid.addColumn(new ComponentRenderer<>(this::createOwnerInfo))
				.setHeader("Owner")
				.setComparator((account1, account2) ->
					account1.getOwner().compareToIgnoreCase(account2.getOwner()))
				.setSortable(true)
				.setWidth("200px");
		grid.addColumn(new ComponentRenderer<>(this::createBankInfo))
				.setComparator(BankAccount::getAccount)
				.setHeader("Bank Account")
				.setWidth("200px");
		grid.addColumn(new ComponentRenderer<>(this::createAvailability))
				.setAutoWidth(true)
				.setComparator(BankAccount::getAvailability)
				.setFlexGrow(0)
				.setHeader("Availability ($)")
				.setTextAlign(ColumnTextAlign.END);
		grid.addColumn(new LocalDateRenderer<>(BankAccount::getUpdated, DateTimeFormatter.ofPattern("MMM dd, YYYY")))
				.setAutoWidth(true)
				.setComparator(BankAccount::getUpdated)
				.setFlexGrow(0)
				.setHeader("Updated");

		return grid;
	}

	private BankAccountMobileTemplate getMobileTemplate(BankAccount bankAccount) {
		return new BankAccountMobileTemplate(bankAccount);
	}

	private Component createOwnerInfo(BankAccount bankAccount) {
		ListItem item = new ListItem(bankAccount.getOwner());
		item.setPadding(Vertical.XS);
		item.setPrefix(new Image(bankAccount.getLogoPath(), "Company logo"));
		item.setSpacing(Right.M);
		return item;
	}

	private Component createBankInfo(BankAccount bankAccount) {
		ListItem item = new ListItem(bankAccount.getAccount(), bankAccount.getBank());
		item.setPadding(Vertical.XS);
		return item;
	}

	private Label createAvailability(BankAccount bankAccount) {
		Double availability = bankAccount.getAvailability();
		Label amountLabel = UIUtils.createAmountLabel(availability);
		if (availability > 0) {
			UIUtils.setTextColor(TextColor.SUCCESS, amountLabel);
		} else {
			UIUtils.setTextColor(TextColor.ERROR, amountLabel);
		}
		return amountLabel;
	}

	private void viewDetails(BankAccount bankAccount) {
		UI.getCurrent().navigate(AccountDetails.class, bankAccount.getId());
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		getUI().ifPresent(ui -> {
			Page page = ui.getPage();
			resizeListener = page.addBrowserWindowResizeListener(event -> updateVisibleColumns(event.getWidth()));
			page.retrieveExtendedClientDetails(details -> updateVisibleColumns(details.getBodyClientWidth()));
		});
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		resizeListener.remove();
		super.onDetach(detachEvent);
	}

	private void updateVisibleColumns(int width) {
		boolean mobile = width < MOBILE_BREAKPOINT;
		List<Grid.Column<BankAccount>> columns = grid.getColumns();

		// "Mobile" column
		columns.get(0).setVisible(mobile);

		// "Desktop" columns
		for (int i = 1; i < columns.size(); i++) {
			columns.get(i).setVisible(!mobile);
		}
	}

	/**
	 * A layout for displaying BankAccount info in a mobile friendly format.
	 */
	private class BankAccountMobileTemplate extends FlexBoxLayout {

		private BankAccount bankAccount;

		public BankAccountMobileTemplate(BankAccount bankAccount) {
			this.bankAccount = bankAccount;

			UIUtils.setLineHeight(LineHeight.M, this);
			UIUtils.setPointerEvents(PointerEvents.NONE, this);

			setPadding(Vertical.S);
			setSpacing(Right.L);

			Image logo = getLogo();
			FlexBoxLayout owner = getOwner();
			Label account = getAccount();
			FlexBoxLayout availability = getAvailability();

			FlexBoxLayout column = new FlexBoxLayout(owner, account, availability);
			column.setFlexDirection(FlexDirection.COLUMN);
			column.setOverflow(Overflow.HIDDEN);

			add(logo, column);
			setFlexGrow(1, column);
		}

		private Image getLogo() {
			Image logo = new Image(bankAccount.getLogoPath(), "Company logo");
			setFlexShrink("0", logo);
			logo.setHeight(LumoStyles.IconSize.M);
			logo.setWidth(LumoStyles.IconSize.M);
			return logo;
		}

		private FlexBoxLayout getOwner() {
			Label owner = UIUtils.createLabel(FontSize.M, TextColor.BODY, bankAccount.getOwner());
			UIUtils.setOverflow(Overflow.HIDDEN, owner);
			UIUtils.setTextOverflow(TextOverflow.ELLIPSIS, owner);

			Badge id = new Badge(String.valueOf(bankAccount.getId()), BadgeColor.NORMAL, BadgeSize.S, BadgeShape.PILL);

			FlexBoxLayout wrapper = new FlexBoxLayout(owner, id);
			wrapper.setAlignItems(Alignment.CENTER);
			wrapper.setFlexGrow(1, owner);
			wrapper.setFlexShrink("0", id);
			wrapper.setSpacing(Right.M);
			return wrapper;
		}

		private Label getAccount() {
			Label account = UIUtils.createLabel(FontSize.S, TextColor.SECONDARY, bankAccount.getAccount());
			account.addClassNames(LumoStyles.Margin.Bottom.S);
			UIUtils.setOverflow(Overflow.HIDDEN, account);
			UIUtils.setTextOverflow(TextOverflow.ELLIPSIS, account);
			return account;
		}

		private FlexBoxLayout getAvailability() {
			Label availability = createAvailability(bankAccount);
			availability.setText("$" + availability.getText());
			Label updated = UIUtils.createLabel(FontSize.XS, TextColor.TERTIARY, UIUtils.formatDate(bankAccount.getUpdated()));

			FlexBoxLayout wrapper = new FlexBoxLayout(availability, updated);
			wrapper.setAlignItems(Alignment.BASELINE);
			wrapper.setFlexGrow(1, availability);
			return wrapper;
		}
	}
}
