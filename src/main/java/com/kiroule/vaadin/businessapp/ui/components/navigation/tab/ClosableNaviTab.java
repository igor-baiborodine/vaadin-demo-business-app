package com.kiroule.vaadin.businessapp.ui.components.navigation.tab;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.kiroule.vaadin.businessapp.ui.util.UIUtils;

public class ClosableNaviTab extends NaviTab {

    private final Button close;

    public ClosableNaviTab(String label,
            Class<? extends Component> navigationTarget) {
        super(label, navigationTarget);
        getElement().setAttribute("closable", true);

        close = UIUtils.createButton(VaadinIcon.CLOSE, ButtonVariant.LUMO_SMALL,
                ButtonVariant.LUMO_TERTIARY);
        add(close);
    }

    public Button getCloseButton() {
        return close;
    }
}
