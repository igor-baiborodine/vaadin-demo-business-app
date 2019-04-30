package com.kiroule.vaadin.businessapp.ui.components.navigation.drawer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.startup.FakeBrowser;
import com.kiroule.vaadin.businessapp.ui.util.UIUtils;

public class NaviItem extends Div {

    protected final String CLASS_NAME = "navi-item";

    private String text;
    private Class<? extends Component> navigationTarget;

    protected Button expandCollapse;

    private boolean subItemsVisible;
    private List<NaviItem> subItems;

    private VaadinIcon down = VaadinIcon.CARET_DOWN;
    private VaadinIcon up = VaadinIcon.CARET_UP;

    private int level = 0;

    public NaviItem(VaadinIcon icon, String text,
            Class<? extends Component> navigationTarget) {
        this(text, navigationTarget);
        link.getElement().insertChild(0, new Icon(icon).getElement());
    }

    public NaviItem(Image image, String text,
            Class<? extends Component> navigationTarget) {
        this(text, navigationTarget);
        link.getElement().insertChild(0, image.getElement());
    }

    public NaviItem(String svg, String text,
            Class<? extends Component> navigationTarget) {
        this(text, navigationTarget);
        try {
            String content = readFile(svg);
            link.getElement().insertChild(0, createSVGContainer(content));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public NaviItem(String text, Class<? extends Component> navigationTarget) {
        setClassName(CLASS_NAME);

        this.text = text;
        this.navigationTarget = navigationTarget;

        expandCollapse = UIUtils.createButton(up, ButtonVariant.LUMO_SMALL,
                ButtonVariant.LUMO_TERTIARY);
        expandCollapse.setVisible(false);
        expandCollapse.addClickListener(
                event -> setSubItemsVisible(!subItemsVisible));
        add(expandCollapse);
        subItemsVisible = true;
        updateAriaLabel();
        subItems = new ArrayList<>();

        setLevel(0);

        if (navigationTarget != null) {
            RouterLink routerLink = new RouterLink(null, navigationTarget);
            routerLink.add(new Label(text));
            routerLink
                    .setHighlightCondition(HighlightConditions.sameLocation());
            routerLink.setClassName(CLASS_NAME + "__link");
            this.link = routerLink;
        } else {
            Div div = new Div(new Label(text));
            div.setClassName(CLASS_NAME + "__link");
            div.addClickListener(e -> expandCollapse.click());
            this.link = div;
        }

        getElement().insertChild(0, link.getElement());
    }

    public void addSubItem(NaviItem item) {
        if (!expandCollapse.isVisible()) {
            expandCollapse.setVisible(true);
        }
        item.setLevel(getLevel() + 1);
        subItems.add(item);
    }

    public void setLevel(int level) {
        this.level = level;
        if (level > 0) {
            getElement().setAttribute("level", Integer.toString(level));
        }
    }

    public int getLevel() {
        return level;
    }

    public String getText() {
        return text;
    }

    public Class<? extends Component> getNavigationTarget() {
        return navigationTarget;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        // If true, we only update the icon. If false, we hide all the sub
        // items.
        if (visible) {
            if (level == 0) {
                expandCollapse.setIcon(new Icon(down));
            }
        } else {
            setSubItemsVisible(visible);
        }
    }

    private void setSubItemsVisible(boolean visible) {
        if (level == 0) {
            expandCollapse.setIcon(new Icon(visible ? up : down));
        }
        subItems.forEach(item -> item.setVisible(visible));
        subItemsVisible = visible;
        updateAriaLabel();
    }


    private void updateAriaLabel(){
        String action;
        if(subItemsVisible) {
            action = "Collapse " + text;
        } else {
            action = "Expand " + text;
        }
        expandCollapse.getElement().setAttribute("aria-label", action);
    }

    public boolean hasSubItems() {
        return subItems.size() > 0;
    }

    protected static String readFile(String path) throws IOException {
        try (InputStream resourceAsStream = VaadinService.getCurrent()
                .getResourceAsStream("frontend://" + path, FakeBrowser.getEs6(),
                        null)) {
            return IOUtils.toString(resourceAsStream, Charsets.UTF_8);
        }
    }

    protected Element createSVGContainer(String content) {
        Div svg = new Div();
        svg.addClassName(CLASS_NAME + "__svg-container");
        svg.getElement().setProperty("innerHTML", content);
        return svg.getElement();
    }

    private final Component link;

    public boolean isHighlighted(AfterNavigationEvent e) {
        return link instanceof RouterLink && ((RouterLink) link)
                .getHighlightCondition().shouldHighlight((RouterLink) link, e);
    }

}
