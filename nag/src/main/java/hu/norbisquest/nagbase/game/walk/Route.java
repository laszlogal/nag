package hu.norbisquest.nagbase.game.walk;

import java.util.ArrayList;
import java.util.List;

public class Route implements RouteInterface {
	private static final int MIN_SIZE = 2;
	private List<WalkPoint> points;
	public Route() {
		points = new ArrayList<>();
	}

	public Route(List<WalkPoint> walkPoints, int step) {
		List<WalkPoint> wp = new ArrayList<>();
		int s = 0;
		for (WalkPoint p : walkPoints) {
			if (s % step == 0) {
				wp.add(p);
			}
			s++;
		}

		if (walkPoints.size() % step != 0) {
			wp.add(walkPoints.get(walkPoints.size() - 1));

		}
		points = wp;
	}
	@Override
	public boolean isValid() {
		return points.size() > MIN_SIZE;
	}
	@Override
	public WalkPoint next() {
		if (!isEnd()) {
			WalkPoint p = points.get(0);
			points.remove(0);
			return p;
		} else {
			return null;
		}
	}

	@Override
	public WalkPoint getPointAt(int index) {
		return (index < points.size() ? points.get(index) : null);
	}

	private boolean isEnd() {
		return points.isEmpty();
	}

	@Override
	public void add(WalkPoint p) {
		points.add(p);
	}

	@Override
	public void add(WalkPoint p, WalkPoint q, String label) {
		//
	}

	@Override
	public void clear() {
		points.clear();
	}

	@Override
	public boolean isEmpty() {
		return points.isEmpty();
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (WalkPoint wp : points) {
			sb.append("    ").append(wp);
		}
		return sb.toString();
	}

	@Override
	public WalkPoint getEnd() {
		return points.get(points.size() - 1);
	}
}
