package com.kiroule.vaadin.bakeryapp.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.kiroule.vaadin.bakeryapp.backend.DummyData;
import com.kiroule.vaadin.bakeryapp.backend.Payment;
import com.kiroule.vaadin.bakeryapp.ui.MainLayout;
import com.kiroule.vaadin.bakeryapp.ui.components.Badge;
import com.kiroule.vaadin.bakeryapp.ui.components.ListItem;
import com.kiroule.vaadin.bakeryapp.ui.components.detailsdrawer.DetailsDrawer;
import com.kiroule.vaadin.bakeryapp.ui.components.detailsdrawer.DetailsDrawerFooter;
import com.kiroule.vaadin.bakeryapp.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.kiroule.vaadin.bakeryapp.ui.components.navigation.bar.AppBar;
import com.kiroule.vaadin.bakeryapp.ui.layout.size.Bottom;
import com.kiroule.vaadin.bakeryapp.ui.util.LumoStyles;
import com.kiroule.vaadin.bakeryapp.ui.util.UIUtils;
import com.kiroule.vaadin.bakeryapp.ui.util.css.FlexDirection;
import com.kiroule.vaadin.bakeryapp.ui.util.css.WhiteSpace;

@Route(value = "payments", layout = MainLayout.class)
@PageTitle("Payments")
public class Payments extends SplitViewFrame {

    private Grid<Payment> grid;
    private ListDataProvider<Payment> dataProvider;
    private DetailsDrawer detailsDrawer;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        initAppBar();
        setViewContent(createContent());
        setViewDetails(createDetailsDrawer());
        filter();
    }

    private void initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();
        for (Payment.Status status : Payment.Status.values()) {
            appBar.addTab(status.getName());
        }
        appBar.addTabSelectionListener(e -> {
            filter();
            detailsDrawer.hide();
        });
        appBar.centerTabs();
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
        dataProvider = DataProvider.ofCollection(DummyData.getPayments());
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        ComponentRenderer<Badge, Payment> badgeRenderer = new ComponentRenderer<>(
                payment -> {
                    Payment.Status status = payment.getStatus();
                    Badge badge = new Badge(status.getName(),
                            status.getTheme());
                    UIUtils.setTooltip(status.getDesc(), badge);
                    return badge;
                });
        grid.addColumn(badgeRenderer).setHeader("Status")
                .setWidth(UIUtils.COLUMN_WIDTH_S).setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createFromInfo))
                .setHeader("From").setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createToInfo))
                .setHeader("To").setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setHeader("Amount ($)").setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(TemplateRenderer.<Payment> of("[[item.date]]")
                .withProperty("date",
                        payment -> UIUtils.formatDate(payment.getDate())))
                .setHeader("Due Date").setComparator(Payment::getDate)
                .setWidth(UIUtils.COLUMN_WIDTH_M).setFlexGrow(0);

        return grid;
    }

    private Component createFromInfo(Payment payment) {
        ListItem item = new ListItem(payment.getFrom(), payment.getFromIBAN());
        item.setHorizontalPadding(false);
        return item;
    }

    private Component createToInfo(Payment payment) {
        ListItem item = new ListItem(payment.getTo(), payment.getToIBAN());
        item.setHorizontalPadding(false);
        return item;
    }

    private Component createAmount(Payment payment) {
        Double amount = payment.getAmount();
        return UIUtils.createAmountLabel(amount);
    }

    private DetailsDrawer createDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Header
        DetailsDrawerHeader detailsDrawerTitle = new DetailsDrawerHeader(
                "Payment Details", true);

        Tabs tabs = new Tabs(new Tab("Details"), new Tab("Attachments"),
                new Tab("History"));
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);

        detailsDrawer.setHeader(detailsDrawerTitle, tabs);
        detailsDrawer.getHeader().setFlexDirection(FlexDirection.COLUMN);

        // Footer
        DetailsDrawerFooter footer = new DetailsDrawerFooter();
        footer.addCancelListener(e -> detailsDrawer.hide());
        detailsDrawer.setFooter(footer);

        return detailsDrawer;
    }

    private void showDetails(Payment payment) {
        detailsDrawer.setContent(createDetails(payment));
        detailsDrawer.show();
    }

    private Component createDetails(Payment payment) {
        ListItem status = new ListItem(payment.getStatus().getIcon(),
                payment.getStatus().getName(), "Status");

        status.getContent().setAlignItems(FlexComponent.Alignment.BASELINE);
        status.getContent().setSpacing(Bottom.XS);
        UIUtils.setTheme(payment.getStatus().getTheme().getThemeName(),
                status.getPrimary());
        UIUtils.setTooltip(payment.getStatus().getDesc(), status);

        ListItem from = new ListItem(
                UIUtils.createTertiaryIcon(VaadinIcon.UPLOAD_ALT),
                payment.getFrom() + "\n" + payment.getFromIBAN(), "Sender");
        ListItem to = new ListItem(
                UIUtils.createTertiaryIcon(VaadinIcon.DOWNLOAD_ALT),
                payment.getTo() + "\n" + payment.getToIBAN(), "Receiver");
        ListItem amount = new ListItem(
                UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR),
                UIUtils.formatAmount(payment.getAmount()), "Amount");
        ListItem date = new ListItem(
                UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR),
                UIUtils.formatDate(payment.getDate()), "Date");

        for (ListItem item : new ListItem[] { status, from, to, amount,
                date }) {
            item.setReverse(true);
            item.setWhiteSpace(WhiteSpace.PRE_LINE);
        }

        Div details = new Div(status, from, to, amount, date);
        details.addClassName(LumoStyles.Padding.Vertical.S);
        return details;
    }

    private void filter() {
        Tab selectedTab = MainLayout.get().getAppBar().getSelectedTab();
        if (selectedTab != null)
            dataProvider.setFilterByValue(Payment::getStatus, Payment.Status
                    .valueOf(selectedTab.getLabel().toUpperCase()));
    }
}
