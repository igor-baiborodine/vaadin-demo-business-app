package com.kiroule.vaadin.businessapp.ui.components.navigation.bar;

import static com.kiroule.vaadin.businessapp.ui.util.UIUtils.IMG_PATH;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.kiroule.vaadin.businessapp.ui.MainLayout;
import com.kiroule.vaadin.businessapp.ui.components.navigation.tab.NaviTabs;
import com.kiroule.vaadin.businessapp.ui.util.LumoStyles;
import com.kiroule.vaadin.businessapp.ui.util.UIUtils;
import com.kiroule.vaadin.businessapp.ui.views.Home;

public class TabBar extends Composite<FlexLayout> {

    private final String CLASS_NAME = "tab-bar";

    private Button menuIcon;

    private FlexLayout actionItems;
    private Image avatar;

    private Button addTab;
    private NaviTabs tabs;

    public TabBar() {
        getContent().setClassName(CLASS_NAME);
        getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);

        menuIcon = UIUtils.createTertiaryInlineButton(VaadinIcon.MENU);
        menuIcon.removeThemeVariants(ButtonVariant.LUMO_ICON);
        menuIcon.addClassName(CLASS_NAME + "__navi-icon");
        menuIcon.addClickListener(e -> MainLayout.get().getNaviDrawer().toggle());

        avatar = new Image();
        avatar.setClassName(CLASS_NAME + "__avatar");
        avatar.setSrc(IMG_PATH + "avatar.png");

        ContextMenu contextMenu = new ContextMenu(avatar);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem("john.smith@gmail.com",
                e -> System.out.println("Testing..."));
        contextMenu.addItem("john.smith@yahoo.com",
                e -> System.out.println("Testing..."));
        contextMenu.addItem("Settings", e -> System.out.println("Testing..."));
        contextMenu.addItem("Logout", e -> System.out.println("Testing..."));

        addTab = UIUtils.createSmallButton(VaadinIcon.PLUS);
        addTab.addClickListener(e -> tabs
                .setSelectedTab(addClosableTab("New Tab", Home.class)));
        addTab.setClassName(CLASS_NAME + "__add-tab");

        tabs = new NaviTabs();
        tabs.setClassName(CLASS_NAME + "__tabs");

        getContent().add(menuIcon, tabs, addTab, avatar);
    }

    /* === MENU ICON === */

    public Button getMenuIcon() {
        return menuIcon;
    }

    /* === TABS === */

    public void centerTabs() {
        tabs.addClassName(LumoStyles.Margin.Horizontal.AUTO);
    }

    private void configureTab(Tab tab) {
        tab.addClassName(CLASS_NAME + "__tab");
    }

    public Tab addTab(String text) {
        Tab tab = tabs.addTab(text);
        configureTab(tab);
        return tab;
    }

    public Tab addTab(String text,
            Class<? extends Component> navigationTarget) {
        Tab tab = tabs.addTab(text, navigationTarget);
        configureTab(tab);
        return tab;
    }

    public Tab addClosableTab(String text,
            Class<? extends Component> navigationTarget) {
        Tab tab = tabs.addClosableTab(text, navigationTarget);
        configureTab(tab);
        return tab;
    }

    public Tab getSelectedTab() {
        return tabs.getSelectedTab();
    }

    public void setSelectedTab(Tab selectedTab) {
        tabs.setSelectedTab(selectedTab);
    }

    public void updateSelectedTab(String text,
            Class<? extends Component> navigationTarget) {
        tabs.updateSelectedTab(text, navigationTarget);
    }

    public void addTabSelectionListener(
            ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
        tabs.addSelectedChangeListener(listener);
    }

    public int getTabCount() {
        return tabs.getTabCount();
    }

    public void removeAllTabs() {
        tabs.removeAll();
    }

    /* === ADD TAB BUTTON === */

    public void setAddTabVisible(boolean visible) {
        addTab.setVisible(visible);
    }

    /* === ACTION ITEMS === */

    public Component addActionItem(Component component) {
        actionItems.add(component);
        updateActionItemsVisibility();
        return component;
    }

    public Button addActionItem(VaadinIcon icon) {
        Button button = UIUtils.createButton(icon, ButtonVariant.LUMO_SMALL,
                ButtonVariant.LUMO_TERTIARY);
        addActionItem(button);
        return button;
    }

    public void removeAllActionItems() {
        actionItems.removeAll();
        updateActionItemsVisibility();
    }

    /* === UPDATE VISIBILITY === */

    private void updateActionItemsVisibility() {
        actionItems.setVisible(actionItems.getComponentCount() > 0);
    }
}
