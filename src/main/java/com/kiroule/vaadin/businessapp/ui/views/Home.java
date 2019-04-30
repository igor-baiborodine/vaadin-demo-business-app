package com.kiroule.vaadin.businessapp.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.kiroule.vaadin.businessapp.ui.MainLayout;
import com.kiroule.vaadin.businessapp.ui.components.FlexBoxLayout;
import com.kiroule.vaadin.businessapp.ui.layout.size.Horizontal;
import com.kiroule.vaadin.businessapp.ui.layout.size.Vertical;
import com.kiroule.vaadin.businessapp.ui.util.css.FlexDirection;

import java.util.Arrays;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Welcome")
public class Home extends ViewFrame {

    public Home() {
        setId("home");
        setViewContent(createContent());
    }

    private Component createContent() {
        Html intro = new Html(
                "<o>A responsive application template with some dummy data. Loosely based on the <b>responsive layout grid</b> guidelines set by <a href=\"https://material.io/design/layout/responsive-layout-grid.html\">Material Design</a>. Utilises the <a href=\"https://vaadin.com/themes/lumo\">Lumo</a> theme.</p>");

        Html productivity = new Html(
                "<span><p>The starter gives you a productivity boost and a head-start. You get an app shell with a typical hierarchical left-hand menu. The shell, the views and the components are all responsive and touch friendly, which makes them great for desktop and mobile use. The views are built with Java, which enhances Java developers' productivity by allowing them to do all in one language.</p>" +
                        "<p>The app comes with multiple list views to edit master-detail data. Views can be divided horizontally or vertically to open up the details, and the the details can also be split into multiple tabs for extra space. The details can also be opened fullscreen to maximize the use of space. Additionally there is an opt-in option for opening multiple application views in tabs within the app, for quick comparison or navigation between data. You enable this feature by setting MainLayout.navigationTabs to true.</p></span>");


        Html documentationLink = new Html("<a href=\"https://vaadin.com/docs/businessapp/overview.html\">Read the documentation");
        Html separator = new Html("<span>&#124;</span>");
        Html starterLink = new Html("<a href=\"https://vaadin.com/start/latest/business-app\">Start a new project with Business App");

        FlexBoxLayout links = new FlexBoxLayout(documentationLink, separator, starterLink);
        links.setFlexDirection(FlexDirection.ROW);
        links.setWidth("100%");
        links.setPadding(Horizontal.RESPONSIVE_L, Vertical.L);
        links.getStyle().set("justify-content", "space-evenly");
        links.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        links.getStyle().set("font-weight", "bold");

        FlexBoxLayout content = new FlexBoxLayout(intro, productivity, links);
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO);
        content.setMaxWidth("840px");
        content.setPadding(Horizontal.RESPONSIVE_L, Vertical.L);
        return content;
    }

}
