package com.kiroule.vaadin.bakeryapp.ui.components.navigation.drawer;

import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;

public class NaviMenu extends Div {
    private static final String CLASS_NAME = "navi-menu";

    public NaviMenu() {
        setClassName(CLASS_NAME);
    }

    protected void addNaviItem(NaviItem item) {
        add(item);
    }

    protected void addNaviItem(NaviItem parent, NaviItem item) {
        parent.addSubItem(item);
        addNaviItem(item);
    }

    public void filter(String filter) {
        getNaviItems().forEach(naviItem -> {
            boolean matches = ((NaviItem) naviItem).getText().toLowerCase()
                    .contains(filter.toLowerCase());
            naviItem.setVisible(matches);
        });
    }

    public NaviItem addNaviItem(VaadinIcon icon, String text,
            Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(icon, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviItem addNaviItem(Image image, String text,
            Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(image, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviItem addNaviItem(String path, String text,
            Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(path, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviItem addNaviItem(NaviItem parent, String text,
            Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(text, navigationTarget);
        addNaviItem(parent, item);
        return item;
    }

    public List<NaviItem> getNaviItems() {
        List<NaviItem> items = (List) getChildren()
                .collect(Collectors.toList());
        return items;
    }

}
