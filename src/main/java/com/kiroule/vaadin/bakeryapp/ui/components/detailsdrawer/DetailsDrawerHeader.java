package com.kiroule.vaadin.bakeryapp.ui.components.detailsdrawer;

import com.vaadin.flow.component.html.Label;
import com.kiroule.vaadin.bakeryapp.ui.util.BoxShadowBorders;
import com.kiroule.vaadin.bakeryapp.ui.util.LumoStyles;
import com.kiroule.vaadin.bakeryapp.ui.util.UIUtils;
import com.kiroule.vaadin.bakeryapp.ui.util.css.BoxSizing;

public class DetailsDrawerHeader extends Label {

    public DetailsDrawerHeader(String title, boolean tabs) {
        super(title);

        // Default styling
        addClassNames(LumoStyles.Heading.H3,
                LumoStyles.Padding.Responsive.Horizontal.L,
                LumoStyles.Padding.Top.L);
        UIUtils.setBoxSizing(BoxSizing.BORDER_BOX, this);
        setWidth("100%");

        // Styling based on whether this component will share the DetailsDrawer
        // header slot with Tabs
        if (tabs) {
            addClassName(LumoStyles.Padding.Bottom.M);
        } else {
            addClassNames(BoxShadowBorders.BOTTOM, LumoStyles.Padding.Bottom.L);
        }
    }

    public DetailsDrawerHeader(String title) {
        this(title, false);
    }

}
