package hu.norbisquest.nagbase.game.walk;

import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.layer.Layer;

public interface WalkAlgorithm extends Destroyable {
	RouteInterface getRoute(double x0, double y0, double x1, double y1);
	void initRoute(double x0, double y0, double x1, double y1, int step);
	void buildRoute();
	RouteInterface getRoute();
	boolean isRouteReady();
	WalkPoint walkPointAt(double x, double y);
	boolean isValid();
	void setScaleRange(double min, double max);
	double getScale(double y);
	int getStep();
	void visualize(Layer layer);
    void reset();
}
