package hu.norbisquest.nagbase.game.walk;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.core.NAGImage;
import hu.norbisquest.nagbase.game.App;

public class Mask extends NAGImage {
	private ImageData data;
	private int bottom;
	private int top;
	private int maskedHeight;
	
	public Mask(ImageResource maskImage) {
		super(maskImage);
	}
	
	@Override
	public void onLoad(LoadEvent event) {
		super.onLoad(event);
		data = getImageData();
		for (int y = 0;y < getHeight(); y++) {
			if (isEdgeAtRow(y)) {
				top = y;
				break;
			}
        }
	
		for (int y = (int)getHeight(); y > 0; y--) {
			if (isEdgeAtRow(y)) {
				bottom = y;
				break;
			}
        }
		maskedHeight = bottom - top;
		
		App.debug("Top: " + top + " Bottom: " + bottom);
		
		for (int x = 0;x < getWidth(); x++) {
			scanEdge(x, 0);
		}
		
	}

	private boolean isEdgeAtRow(int y) {
		for (int x = 0;x < getWidth(); x++) {
			if (hasMaskAt(x, y)) {
				return true;
			}
        }
		return false;
	}
	boolean hasMaskAt(int x, int y) {
		return data.getAlphaAt(x, y) != 0;
	}

	private boolean isEdgePoint(int x, int y) {
		int neighbours = -1; 
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (hasMaskAt(x + i, y + j)) {
					neighbours++;
				}		
			}	
		}
		
		return neighbours > 0 && neighbours != 8;
	}

	private void scanEdge(int x, int y0) {
		for(int y = y0 ;y < bottom; y++) {
			if (isEdgePoint(x, y)) {
				data.setRedAt(128, x, y);
				data.setGreenAt(128, x, y);
				data.setBlueAt(128, x, y);
			}
        }
	}

	private boolean hasEdgeAt(int x, int y) {
		return (data.getRedAt(x, y) == 128);
	}

	public void visualize() {
		if (!isLoaded()) {
			return;
		}
		clear();

		Context2d ctx = getContext2d();
		ctx.putImageData(data, 0, 0);
	}
	
	public int getTop() {
		return top;
	}
	
	public int getBottom() {
		return bottom;
	}
	/**
	 * @return the maskedHeight
	 */
	public int getMaskedHeight() {
		return maskedHeight;
	}
	
	public int getEdgeYAt(int x, int y) {
		int y0 = y > top ? y : top;
		for (int y1 = y0; y1 < bottom; y1++) {
			if (hasMaskAt(x, y1)) {
				return y1;
			}
		}

		for (int y1 = y0; y1 > top; y1--) {
			if (hasMaskAt(x, y1)) {
				return y1;
			}
		}

		return -1;
	}
	
}

