package hu.norbisquest.nagbase.game.gui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

import java.util.ArrayList;
import java.util.List;

public class NAGListBox extends ScrollPanel implements ClickHandler, HasChangeHandlers {
    private FlowPanel main = new FlowPanel();
    private List<Label> items = new ArrayList<>();
    private List<String> texts = new ArrayList<>();
    private Label selected;

    NAGListBox() {
        addStyleName("nagListBox");
        main.addStyleName("options");
        setWidget(main);
    }

    void addItem(String text) {
        Label item = new Label(text);
        item.setStyleName("item");
        item.addClickHandler(this);
        items.add(item);
        texts.add(text);
        main.add(item);
    }

    @Override
    public void clear() {
        items.clear();
        main.clear();
    }

    @Override
    public void onClick(ClickEvent event) {
        unselect();
        Object src = event.getSource();
        if (!(src instanceof Label)) {
            return;
        }

        int idx = items.indexOf(src);
        if (idx != -1) {
            selectItem(idx);

        }
    }

    private void unselect() {
        if (selected == null) {
            return;
        }
        selected.removeStyleName("selected");
        selected = null;
    }

    private void selectItem(int idx) {
        Label item = items.get(idx);
        if (item != null) {
            selected = item;
            selected.addStyleName("selected");
            NativeEvent event = Document.get().createChangeEvent();
            DomEvent.fireNativeEvent(event, this);
        }
    }


    private int getSelectedItemIndex() {
        return items.indexOf(selected);
    }

    public String getSelectedItemText() {
        int idx = getSelectedItemIndex();
        return idx == -1 ? "" : texts.get(idx);
    }

    @Override
    public HandlerRegistration addChangeHandler(ChangeHandler handler) {
        return addDomHandler(handler, ChangeEvent.getType());
    }
}
