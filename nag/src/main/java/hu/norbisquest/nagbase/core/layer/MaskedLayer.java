package hu.norbisquest.nagbase.core.layer;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.Composite;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.resources.client.ImageResource;
import hu.norbisquest.nagbase.core.Loadable;
import hu.norbisquest.nagbase.core.NAGCanvas;
import hu.norbisquest.nagbase.core.NAGImage;
import hu.norbisquest.nagbase.game.App;

import java.util.ArrayList;
import java.util.List;

public class MaskedLayer extends Layer implements Loadable {
	private class Baseline {
		// The Y coordinate which gives the baseline
		private double y;

		// Flag that decides if the picture needs to be hidden from above,
		// or below the baseline
		private boolean top;

		Baseline(double y, boolean top) {
			this.y = y;
			this.top = top;
		}
	}

	private NAGImage topImage;
	private NAGImage bottomImage;
	private List<Baseline> baselines;
	private boolean maskEnable;
	private boolean sourceCheck = true;

	public MaskedLayer(int zIndex) {
		setZIndex(zIndex);
		baselines = new ArrayList<>();
		maskEnable = true;
	}

	public MaskedLayer(ImageResource topRes, ImageResource bottomRes,
			int zIndex) {
		this(zIndex);
		setTopMask(topRes);
		setBottomMask(bottomRes);

	}

	public void setTopMask(ImageResource res) {
		topImage = res != null ? new NAGImage(res) : null;

	}

	public void setBottomMask(ImageResource res) {
		bottomImage = res != null ? new NAGImage(res) : null;

	}

	public void setTopMask(NAGImage img) {
		topImage = img;

	}

	public void setBottomMask(NAGImage img) {
		bottomImage = img;

	}

	private void addBaseLine(double y, boolean top) {
		baselines.add(new Baseline(y, top));
	}

	@Override
	public void draw(CanvasElement elem, double x, double y) {
		int w = elem.getWidth();
		int h = elem.getHeight();
		double sx = x;
		double sy = y;
		// TODO: UGLY HACK - this case should be always good!
		if (isSourceCheck()) {
			if (x < 0) {
				sx = 0;
				if (w + x > 0) {
					w += (int) x;
				} else {
					return;
				}
			}

            if (sx + w > getWidth()) {
				w = (int) (getWidth() - sx);
			}

			if (y < 0) {
				sy = 0;
				if (h + y > 0) {
					h += (int) y;
				} else {
					return;
				}
			}

            if (sy + h > App.getHeight()) {
				h = (int) (App.getHeight() - sy);
			}

			if (h == 0 || w == 0 || sx > getWidth() || sy > App.getHeight()) {
				return;
			}
		}
		drawMasked(elem, x, y, sx, sy, w, h);
	}

	private void drawMasked(CanvasElement elem, double x, double y, double sx,
                            double sy, int w, int h) {
		CanvasElement canvasElem;
		Context2d ctx = getContext2d();

		ctx.save();
		boolean negative = sx != x || sy != y;
		Baseline line = maskLine(y + h);
		if (maskEnable && line != null) {
			NAGImage img = line.top ? topImage : bottomImage;
			if (img == null || !img.isLoaded()) {
				return;
			}
			Canvas c = NAGCanvas.createCanvas(w, h);
			Context2d tmpCtx = c.getContext2d();
			tmpCtx.drawImage(img.asCanvasElement(), (int) sx, (int) sy, w, h, 0, 0, w, h);
			tmpCtx.setGlobalCompositeOperation(Composite.SOURCE_OUT);
			if (negative) {
				tmpCtx.drawImage(elem, x < 0 ? x : 0, y < 0 ? y : 0);

			} else {
				tmpCtx.drawImage(elem, 0, 0);
			}
			canvasElem = c.getCanvasElement();
		} else {
			canvasElem = elem;
		}

		if (negative) {
			ctx.drawImage(canvasElem, sx, sy);
		} else {
			ctx.drawImage(canvasElem, x, y);
		}

		ctx.restore();

	}

	private Baseline maskLine(double bottomLine) {
		for (Baseline line : baselines) {
			if ((line.top && bottomLine < line.y)
					|| (!line.top && bottomLine > line.y)) {
				return line;
			}
		}
		return null;
	}

	@Override
	public boolean load() {
		baselines.clear();
		if (topImage != null) {
			topImage.addLoadHandler(event -> {
                int bottom = topImage.findBottom();
                addBaseLine(bottom, true);
                App.debug("[MASK] TOP: bottom: " + bottom);
            });

			topImage.load();

		}

		if (bottomImage != null) {
			bottomImage.addLoadHandler(event -> {
                int top = bottomImage.findTop();
                addBaseLine(top, false);
                App.debug("[MASK] BOTTOM: top: " + top);

            });
			bottomImage.load();
		}
	return true;
	}

	public void refresh() {
		baselines.clear();
		load();
	}

	@Override
	public boolean unload() {
		if (topImage != null) {
			topImage.unload();
		}

		if (bottomImage != null) {
			bottomImage.unload();
		}
		return true;
	}

	private void load(NAGImage img) {
		if (img != null) {
			img.load();
		}
	}

	@Override
	public void doDestroy() {
		App.infoDestroy("maskedLayer");
		if (topImage != null) {
			App.infoDestroy("topImage");
			topImage.destroy();

		}

		if (bottomImage != null) {
			App.infoDestroy("bottomImage");
			bottomImage.destroy();
		}
		topImage = null;
		bottomImage = null;
		baselines.clear();
		baselines = null;
	}

	public boolean isMaskEnable() {
		return maskEnable;
	}

	public void setMaskEnable(boolean maskEnable) {
		this.maskEnable = maskEnable;
	}

	private boolean isSourceCheck() {
		return sourceCheck;
	}

	public void setSourceCheck(boolean sourceCheck) {
		this.sourceCheck = sourceCheck;
	}
}
