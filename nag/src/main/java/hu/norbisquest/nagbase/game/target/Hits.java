package hu.norbisquest.nagbase.game.target;

import hu.norbisquest.nagbase.common.game.target.Target;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hits {
	private class TargetComparator implements Comparator<hu.norbisquest.nagbase.common.game.target.Target> {

		@Override
		public int compare(hu.norbisquest.nagbase.common.game.target.Target o1, hu.norbisquest.nagbase.common.game.target.Target o2) {
			if (o1.getZIndex() == o2.getZIndex()) {
				return 0;
			}

			return o1.getZIndex() < o2.getZIndex() ? 1 : -1;
		}
	}

	private List<hu.norbisquest.nagbase.common.game.target.Target> targets = new ArrayList<>();

	public Hits() {

	}

	public void add(hu.norbisquest.nagbase.common.game.target.Target target) {
		targets.add(target);
	}

	public Target getTopHit() {
		targets.sort(new TargetComparator());
		return targets.get(0);
	}

	public boolean isEmpty() {
		return targets.isEmpty();
	}
}
