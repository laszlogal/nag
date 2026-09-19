package hu.norbisquest.nagbase.core.layer;

import com.google.gwt.event.dom.client.*;

@SuppressWarnings("EmptyMethod")
public interface EventLayerListener {
	void onMouseMove(int x, int y);

	void onMouseOut(int x, int y);

	void onClick(int x, int y);

	void onLongPress(int x, int y);

	void onKeyDown(KeyDownEvent event);

	void onContextMenu(ContextMenuEvent event);

	void onTouchStart(TouchStartEvent event);

	void onTouchMove(TouchMoveEvent event);

	void onTouchEnd(TouchEndEvent event);

	void onDrop(int x, int y);

}
