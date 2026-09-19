package hu.norbisquest.nqcommon.client.dialog;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import hu.norbisquest.nagbase.common.engine.Command;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.App.GameModes;
import hu.norbisquest.nagbase.game.Stage;
import hu.norbisquest.nagbase.game.gui.Scalable;
import hu.norbisquest.nagbase.game.target.InventoryItem;
import hu.norbisquest.nagbase.game.target.InventoryView;
import hu.norbisquest.nagbase.game.target.TargetControl;

import java.util.List;

public abstract class NQInventoryDialog extends NQButtonDialog implements InventoryView {

    private static final int TOUCH_OFFSET = 32;
    private static final int HIDE_TRESHOLD = 20;
    private PushButton btnExamine;
    private PushButton btnUse;
    private PushButton btnClose;
    private FlowPanel imagePanel;
    private InventoryItem draggedItem=null;
    private Timer closeTimer;
    private Image dummyItemImage;
    private boolean changed=false;

    protected NQInventoryDialog(Scalable scaler) {
        super(scaler);
        closeTimer = new Timer() {

            @Override
            public void run() {
                if (draggedItem != null) {
                    useItem(draggedItem);
                }
                hide();
            }
        };
        addStyleName("inventoryDialog");
        ImageResource bg = getBackgroundResource();
        if (bg != null) {
            setBackground(bg);
        }
        createItems();
        createButtons();
        addCloseHandler(this);
        dummyItemImage = new Image();
        dummyItemImage.addStyleName("dummyItemImage");
    }

    private void createItems() {
        ScrollPanel itemsPanel = new ScrollPanel();
        itemsPanel.addStyleName("itemsPanel");
        imagePanel = new FlowPanel();
        itemsPanel.add(imagePanel);
        getMain().add(itemsPanel);

        addDomHandler(event ->

        {
            handleCloseOnDrag(App.toScaledX(event.getNativeEvent().getClientX()),
                    App.toScaledY(event.getNativeEvent().getClientY()));
            event.preventDefault();

        },DragOverEvent.getType());

        addDomHandler(event ->closeTimer.cancel(),DragEndEvent.getType());
    }

    protected abstract ImageResource getBackgroundResource();

    protected abstract ImageResource getExamineIconResource();

    protected abstract ImageResource getUseIconResource();

    protected abstract ImageResource getOkIconResource();

    @Override
    protected void createButtons() {
        FlowPanel buttons = new FlowPanel();
        buttons.addStyleName("buttons");
        btnExamine = new PushButton(new Image(getExamineIconResource()));
        btnUse = new PushButton(new Image(getUseIconResource()));

        ImageResource okRes = getOkIconResource();

        if (okRes != null) {
            btnClose = new PushButton(new Image(okRes));
            btnClose.addClickHandler(this);
            btnClose.addStyleName("ok");
        } else {
            btnClose = null;
        }

        buttons.add(btnExamine);
        buttons.add(btnUse);
        if (btnClose != null) {
            buttons.add(btnClose);
        }
        getMain().add(buttons);

        btnExamine.addClickHandler(this);
        btnUse.addClickHandler(this);
    }

    @Override
    public void onClick(ClickEvent event) {
        Object source = event.getSource();
        if (source == btnExamine) {
            App.setMode(GameModes.EXAMINE);
        } else if (source == btnUse) {

            App.setMode(GameModes.USE);
        }
        if (source == btnClose) {
            onClose(null);
            hide();
        }

    }

    private void handleSelectedItem(InventoryItem selectedItem) {
        App.debug("Inventory click");
        TargetControl ctrl = selectedItem.getControl();
        if (ctrl == null) {
            return;
        }
        GameModes mode = App.getMode();
        List<Command> cmdList = null;
        switch (mode) {
            case EXAMINE:
                cmdList = ctrl.onExamine(0, 0);
                break;
            case INVENTORY:
                cmdList = ctrl.onInventory(App.getInventory().getSelectedItem().getId());
                break;
            case WALK:
            case NORMAL:
            case USE:
                useItem(selectedItem);
                break;

            case TALK:
                break;
            default:
                break;

        }

        feed(cmdList);
    }

    private void useItem(InventoryItem selectedItem) {
        App.getInventory().setSelectedItem(selectedItem);
        App.getCursor().setInventory(selectedItem);
        App.setMode(GameModes.INVENTORY);

    }

    private void feed(List<Command> cmdList) {
        if (cmdList == null) {
            return;
        }

        App.getFeeder().clearCommands();
        App.getFeeder().addCommandList(cmdList);
    }

    private void addItemHandlers(final InventoryItem item) {
        final Image img = item.getImage().getGWTImage();
        img.addClickHandler(event -> handleSelectedItem(item));
        img.addDragStartHandler(event -> {
            draggedItem = item;
            event.setData("text", item.toString());
            event.stopPropagation();
        });

        img.addDropHandler(event -> {
            event.preventDefault();
            handleDrop(item);
        });

        img.addDragOverHandler(DomEvent::preventDefault);
        img.addTouchStartHandler(event -> {
            event.stopPropagation();
            App.debug("touch start: " + item);
            img.addStyleName("itemDragging");
            draggedItem = item;
            dummyItemImage.setUrl(img.getUrl());
            imagePanel.insert(dummyItemImage, imagePanel.getWidgetIndex(img));
            img.getElement().getStyle().setPosition(Position.ABSOLUTE);

            setPositionFromTouch(event, img);
        });

        img.addTouchMoveHandler(event -> {
            event.stopPropagation();
            event.preventDefault();
            App.debug("touch move: " + item);
            setPositionFromTouch(event, img);
        });

        img.addTouchEndHandler(event -> {
            event.stopPropagation();
            App.debug("img touch end - DROP: " + item);
            dummyItemImage.removeFromParent();
            img.getElement().getStyle().clearPosition();
            img.removeStyleName("itemDragging");
            fidItemAtTouch(event);
        });
    }


    void refresh() {
        if (!changed) {
            return;
        }

        imagePanel.clear();
        for (final InventoryItem item : App.getInventory().getItems()) {
            Image img = item.getImage().getGWTImage();
            imagePanel.add(img);
            img.setVisible(true);
            addItemHandlers(item);
        }
        changed = false;
    }

    private void setPositionFromTouch(TouchEvent<?> event, Widget w) {
		Touch t = event.getTargetTouches().get(0);
		Element elem = w.getElement();
		elem.getStyle().setLeft(App.toScaledX(t.getClientX() - getAbsoluteLeft() - TOUCH_OFFSET), Unit.PX);
		elem.getStyle().setTop(App.toScaledY(t.getClientY() - getAbsoluteTop() - TOUCH_OFFSET), Unit.PX);

	}

	private void fidItemAtTouch(TouchEvent<?> event) {
		Touch t = event.getChangedTouches().get(0);
		int x = t.getClientX();
		int y = t.getClientY();
		App.debug("ITEM at (" + x + ", " + y + ")");
		for (InventoryItem item : App.getInventory().getItems()) {
			if (item.isImageHit(x, y)) {
				handleDrop(item);
				return;
			}
		}

	}

	private void handleDrop(InventoryItem item) {
		if (draggedItem == null) {
			return;
			}
		TargetControl ctrl = item.getControl();
		if (ctrl != null) {
			feed(ctrl.onInventory(draggedItem.getId()));
		}
		draggedItem = null;

	}

	private void handleCloseOnDrag(int x, int y) {
		int top = App.toScaledX(getAbsoluteTop());
		int left = App.toScaledY(getAbsoluteLeft());
		int right = left + getOffsetWidth();
		int bottom = top + getOffsetHeight();

		if (y - top < HIDE_TRESHOLD || bottom - y < HIDE_TRESHOLD
				|| x - left < HIDE_TRESHOLD || right - x < HIDE_TRESHOLD) {
			closeTimer.schedule(50);
		} else {
			closeTimer.cancel();
		}

	}

	@Override
	public void onContextMenu(ContextMenuEvent event) {
		super.onContextMenu(event);
		if (App.getMode() == GameModes.EXAMINE) {
			App.setMode(GameModes.USE);
		} else {
			App.setMode(GameModes.EXAMINE);

		}
	}

	@Override
	public void onClose(CloseEvent event) {
        closeTimer.cancel();
        Stage stage = App.getCurrentStage();
		if (stage != null) {
			stage.onInventoryClose();
		}
	}

	private void onChange() {
        changed = true;
        if (isVisible()) {
            refresh();
        }
    }

    @Override
    public void add(InventoryItem item) {
        if (item.getImage().isLoaded()) {
            addItemHandlers(item);
        } else {
            item.getImage().getGWTImage().addLoadHandler(event -> addItemHandlers(item));
        }
        onChange();
    }

    @Override
    public void remove(InventoryItem item) {
        onChange();
    }

}
