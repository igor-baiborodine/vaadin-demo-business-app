package com.kiroule.vaadin.bakeryapp.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.Lumo;
import com.kiroule.vaadin.bakeryapp.ui.layout.size.Size;
import com.kiroule.vaadin.bakeryapp.ui.util.css.BorderRadius;
import com.kiroule.vaadin.bakeryapp.ui.util.css.BoxSizing;
import com.kiroule.vaadin.bakeryapp.ui.util.css.Display;
import com.kiroule.vaadin.bakeryapp.ui.util.css.FlexDirection;
import com.kiroule.vaadin.bakeryapp.ui.util.css.FlexWrap;
import com.kiroule.vaadin.bakeryapp.ui.util.css.Overflow;
import com.kiroule.vaadin.bakeryapp.ui.util.css.Position;
import com.kiroule.vaadin.bakeryapp.ui.util.css.Shadow;

public class FlexBoxLayout extends FlexLayout {

    public static final String BACKGROUND_COLOR = "background-color";
    public static final String BORDER_RADIUS = "border-radius";
    public static final String BOX_SHADOW = "box-shadow";
    public static final String BOX_SIZING = "box-sizing";
    public static final String DISPLAY = "display";
    public static final String FLEX_DIRECTION = "flex-direction";
    public static final String FLEX_WRAP = "flex-wrap";
    public static final String MAX_WIDTH = "max-width";
    public static final String OVERFLOW = "overflow";
    public static final String POSITION = "position";

    public FlexBoxLayout(Component... components) {
        super(components);
    }

    public void setBackgroundColor(String value) {
        getStyle().set(BACKGROUND_COLOR, value);
    }

    public void setBackgroundColor(String value, String theme) {
        getStyle().set(BACKGROUND_COLOR, value);
        setTheme(theme);
    }

    public void removeBackgroundColor() {
        getStyle().remove(BACKGROUND_COLOR);
    }

    public void setBorderRadius(BorderRadius radius) {
        getStyle().set(BORDER_RADIUS, radius.getValue());
    }

    public void removeBorderRadius() {
        getStyle().remove(BORDER_RADIUS);
    }

    public void setBoxSizing(BoxSizing sizing) {
        getStyle().set(BOX_SIZING, sizing.getValue());
    }

    public void removeBoxSizing() {
        getStyle().remove(BOX_SIZING);
    }

    public void setDisplay(Display display) {
        getStyle().set(DISPLAY, display.getValue());
    }

    public void removeDisplay() {
        getStyle().remove(DISPLAY);
    }

    public void setFlex(String value, Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("flex", value);
        }
    }

    public void setFlexBasis(String value, Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("flex-basis", value);
        }
    }

    public void setFlexDirection(FlexDirection direction) {
        getStyle().set(FLEX_DIRECTION, direction.getValue());
    }

    public void removeFlexDirection() {
        getStyle().remove(FLEX_DIRECTION);
    }

    public void setFlexShrink(String value, Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("flex-shrink", value);
        }
    }

    public void setFlexWrap(FlexWrap wrap) {
        getStyle().set(FLEX_WRAP, wrap.getValue());
    }

    public void removeFlexWrap() {
        getStyle().remove(FLEX_WRAP);
    }

    public void setMargin(Size... sizes) {
        for (Size size : sizes) {
            for (String attribute : size.getMarginAttributes()) {
                getStyle().set(attribute, size.getVariable());
            }
        }
    }

    public void removeMargin() {
        getStyle().remove("margin");
        getStyle().remove("margin-bottom");
        getStyle().remove("margin-left");
        getStyle().remove("margin-right");
        getStyle().remove("margin-top");
    }

    public void setMaxWidth(String value) {
        getStyle().set(MAX_WIDTH, value);
    }

    public void removeMaxWidth() {
        getStyle().remove(MAX_WIDTH);
    }

    public void setOverflow(Overflow overflow) {
        getStyle().set(OVERFLOW, overflow.getValue());
    }

    public void removeOverflow() {
        getStyle().remove(OVERFLOW);
    }

    public void setPadding(Size... sizes) {
        for (Size size : sizes) {
            for (String attribute : size.getPaddingAttributes()) {
                getStyle().set(attribute, size.getVariable());
            }
        }
    }

    public void removePadding() {
        getStyle().remove("padding");
        getStyle().remove("padding-bottom");
        getStyle().remove("padding-left");
        getStyle().remove("padding-right");
        getStyle().remove("padding-top");
    }

    public void setPosition(Position position) {
        getStyle().set(POSITION, position.getValue());
    }

    public void removePosition() {
        getStyle().remove(POSITION);
    }

    public void setShadow(Shadow shadow) {
        getStyle().set(BOX_SHADOW, shadow.getValue());
    }

    public void removeShadow() {
        getStyle().remove(BOX_SHADOW);
    }

    public void setSpacing(Size... sizes) {
        for (Size size : sizes) {
            addClassName(size.getSpacingClassName());
        }
    }

    public void setTheme(String theme) {
        if (Lumo.DARK.equals(theme)) {
            getElement().setAttribute("theme", "dark");
        } else {
            getElement().removeAttribute("theme");
        }
    }
}
