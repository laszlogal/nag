package hu.norbisquest.nagbase.game.walk;

import hu.norbisquest.nagbase.game.App;

import java.util.ArrayList;
import java.util.List;

class RouteBuilder {

	private static final int LINE_STEP = 1;
	public static final int minDist = 2;

	public interface BuildRoute {
		void appendRoute(List<WalkPoint> points, int x, int y);
	}
	
	private BuildRoute listener;
	public RouteBuilder(BuildRoute listener) {
		this.listener = listener;
	}

	public List<WalkPoint> build(WalkPoint p, WalkPoint q) {
		return build(p.getX(), p.getY(), q.getX(), q.getY());
	}

	private List<WalkPoint> build(double x0, double y0, double x1, double y1) {
		return build((int)x0, (int)y0, (int)x1, (int)y1);
	}	

	private List<WalkPoint> build(int x0, int y0, int x1, int y1) {
		List<WalkPoint> points = new ArrayList<>();
		//return build2(x0, y0, x1, y1, points);
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int stepX = x1 < x0 ? -LINE_STEP: LINE_STEP;
		int stepY = y1 < y0 ? -LINE_STEP: LINE_STEP;
		listener.appendRoute(points, x0,y0);
		if (dx > dy) {
			int y = y0;
			int d = 2 * dy - dx;
			for (int x = x0 + stepX; (stepX == LINE_STEP? x < x1:  x1 < x); x += stepX) {
				if (d > 0) {
					y += stepY;
					listener.appendRoute(points, x, y);
					d +=  (2 * dy - 2 * dx);
				}
				else {
					listener.appendRoute(points, x,y);
					d += 2 * dy;
				}
			}
		} else {
			int x = x0;
			int d = 2 * dx - dy;
			for (int y = y0 + stepY; (stepY == LINE_STEP? y < y1:  y1 < y); y += stepY) {
				if (d > 0) {
					x += stepX;
					listener.appendRoute(points,x, y);
					d +=  (2 * dx - 2 * dy);
				}
				else {
					listener.appendRoute(points, x,y);
					d += 2 * dx;
				}
			}
		}
		listener.appendRoute(points, x1, y1);
		return points;
	}


	private List<WalkPoint> build2(int x0, int y0, int x1, int y1, List<WalkPoint> points) {
		App.debug("build2(" + x0 + ", " + y0 + ", " + x1 + ", " + y1 + ")");
		int midX = x0 + Math.abs(x1-x0)/2;
		int midY = y0 + Math.abs(y1-y0)/2;
		App.debug("Middle (" + midX + ", " + midY + ")");
		listener.appendRoute(points, x0, y0);
		listener.appendRoute(points, midX, midY);
		listener.appendRoute(points, x1, y1);
		if (!samePoint(x0, y0, midX, midY)) {
			return build2(x0, y0, midX, midY, points);

		}
		if (!samePoint(midX, midY, x1, y1)) {
			return build2(midX, midY, x1, y1, points);
		}

		return points;
	}

	private boolean samePoint(int x0, int y0, int x1, int y1) {
		return (Math.abs(x0 - x1) < 2 && Math.abs(y0 - y1) < 2);
	}
}
