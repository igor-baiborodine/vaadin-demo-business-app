package com.kiroule.vaadin.bakeryapp.ui.views;

import java.time.LocalDate;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.kiroule.vaadin.bakeryapp.backend.BankAccount;
import com.kiroule.vaadin.bakeryapp.backend.DummyData;
import com.kiroule.vaadin.bakeryapp.ui.MainLayout;
import com.kiroule.vaadin.bakeryapp.ui.components.FlexBoxLayout;
import com.kiroule.vaadin.bakeryapp.ui.components.ListItem;
import com.kiroule.vaadin.bakeryapp.ui.components.navigation.bar.AppBar;
import com.kiroule.vaadin.bakeryapp.ui.layout.size.Bottom;
import com.kiroule.vaadin.bakeryapp.ui.layout.size.Horizontal;
import com.kiroule.vaadin.bakeryapp.ui.layout.size.Top;
import com.kiroule.vaadin.bakeryapp.ui.layout.size.Uniform;
import com.kiroule.vaadin.bakeryapp.ui.layout.size.Vertical;
import com.kiroule.vaadin.bakeryapp.ui.util.BoxShadowBorders;
import com.kiroule.vaadin.bakeryapp.ui.util.LumoStyles;
import com.kiroule.vaadin.bakeryapp.ui.util.TextColor;
import com.kiroule.vaadin.bakeryapp.ui.util.UIUtils;
import com.kiroule.vaadin.bakeryapp.ui.util.css.BorderRadius;
import com.kiroule.vaadin.bakeryapp.ui.util.css.BoxSizing;
import com.kiroule.vaadin.bakeryapp.ui.util.css.FlexDirection;
import com.kiroule.vaadin.bakeryapp.ui.util.css.FlexWrap;
import com.kiroule.vaadin.bakeryapp.ui.util.css.Shadow;
import com.kiroule.vaadin.bakeryapp.ui.util.css.WhiteSpace;

@Route(value = "account-details", layout = MainLayout.class)
@PageTitle("Account Details")
public class AccountDetails extends ViewFrame implements HasUrlParameter<Long> {

    public int RECENT_TRANSACTIONS = 4;

    private ListItem availability;
    private ListItem bankAccount;
    private ListItem updated;

    private BankAccount account;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        AppBar appBar = initAppBar();
        appBar.setTitle(account.getOwner());
        UI.getCurrent().getPage().setTitle(account.getOwner());

        availability.setPrimaryText(
                UIUtils.formatAmount(account.getAvailability()));
        bankAccount.setPrimaryText(account.getAccount());
        bankAccount.setSecondaryText(account.getBank());
        updated.setPrimaryText(UIUtils.formatDate(account.getUpdated()));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        setViewContent(createContent());
        account = DummyData.getBankAccount(id);
    }

    private AppBar initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();
        appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
        appBar.getContextIcon().addClickListener(
                e -> UI.getCurrent().navigate(Accounts.class));
        return appBar;
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createLogoSection(),
                createRecentTransactionsHeader(),
                createRecentTransactionsList(), createMonthlyOverviewHeader(),
                createMonthlyOverviewChart());
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
        content.setMaxWidth("840px");
        return content;
    }

    private FlexBoxLayout createLogoSection() {
        Image image = DummyData.getLogo();
        image.addClassName(LumoStyles.Margin.Horizontal.L);
        UIUtils.setBorderRadius(BorderRadius._50, image);
        image.setHeight("200px");
        image.setWidth("200px");

        availability = new ListItem(
                UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR), "",
                "Availability");
        availability.setId("availability");
        availability.getPrimary().addClassName(LumoStyles.Heading.H2);
        availability.setDividerVisible(true);
        availability.setReverse(true);

        bankAccount = new ListItem(
                UIUtils.createTertiaryIcon(VaadinIcon.INSTITUTION), "", "");
        bankAccount.setId("bankAccount");
        bankAccount.setDividerVisible(true);
        bankAccount.setReverse(true);
        bankAccount.setWhiteSpace(WhiteSpace.PRE_LINE);

        updated = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR),
                "", "Updated");
        updated.setReverse(true);

        FlexBoxLayout listItems = new FlexBoxLayout(availability, bankAccount,
                updated);
        listItems.setFlexDirection(FlexDirection.COLUMN);

        FlexBoxLayout section = new FlexBoxLayout(image, listItems);
        section.addClassName(BoxShadowBorders.BOTTOM);
        section.setAlignItems(FlexComponent.Alignment.CENTER);
        section.setFlex("1", listItems);
        section.setFlexWrap(FlexWrap.WRAP);
        section.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        section.setPadding(Bottom.L);
        return section;
    }

    private Component createRecentTransactionsHeader() {
        Label title = UIUtils.createH3Label("Recent Transactions");

        Button viewAll = UIUtils.createSmallButton("View All");
        viewAll.addClickListener(
                e -> UIUtils.showNotification("Not implemented yet."));
        viewAll.addClassName(LumoStyles.Margin.Left.AUTO);

        FlexBoxLayout header = new FlexBoxLayout(title, viewAll);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
        return header;
    }

    private Component createRecentTransactionsList() {
        Div items = new Div();
        items.addClassNames(BoxShadowBorders.BOTTOM,
                LumoStyles.Padding.Bottom.L);

        for (int i = 0; i < RECENT_TRANSACTIONS; i++) {
            Double amount = DummyData.getAmount();

            Label amountLabel = UIUtils.createAmountLabel(amount);
            if (amount > 0) {
                UIUtils.setTextColor(TextColor.SUCCESS, amountLabel);
            } else {
                UIUtils.setTextColor(TextColor.ERROR, amountLabel);
            }

            ListItem item = new ListItem(DummyData.getLogo(),
                    DummyData.getCompany(),
                    UIUtils.formatDate(LocalDate.now().minusDays(i)),
                    amountLabel);

            // Dividers for all but the last item
            item.setDividerVisible(i < RECENT_TRANSACTIONS - 1);
            items.add(item);
        }

        return items;
    }

    private Component createMonthlyOverviewHeader() {
        Label header = UIUtils.createH3Label("Monthly Overview");
        header.addClassNames(LumoStyles.Margin.Vertical.L,
                LumoStyles.Margin.Responsive.Horizontal.L);
        return header;
    }

    private Component createMonthlyOverviewChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("");
        conf.getLegend().setEnabled(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec");
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

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setFlexWrap(FlexWrap.WRAP);
        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.S);
        return card;
    }
}
