package hu.norbisquest.nagbase.game.walk;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.resources.client.TextResource;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.core.layer.Layer;
import hu.norbisquest.nagbase.game.App;
import hu.norbisquest.nagbase.game.Feature;
import hu.norbisquest.nagbase.game.walk.RouteBuilder.BuildRoute;
import hu.norbisquest.nagbase.game.walk.WalkGraphModel.Edge;
import hu.norbisquest.nagbase.game.walk.WalkGraphModel.Projection;
import hu.norbisquest.nagbase.game.walk.WalkGraphModel.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WalkGraph extends NAGObject implements WalkAlgorithm, BuildRoute {
	private static final int VERTEX_SIZE = 6;
	private static final double EDGE_WIDTH = 2;
	private static final int PROJECTION_WIDTH = 3;
	private static final String LABEL_START = "";
	private static final String LABEL_END = "";
	private static final int DEFAULT_STEP = 1;
	private WalkGraphModel model;

	private Projection vProjection;
	// Route builder
	private RouteBuilder routeBuilder;

	// walk step:
	// Route will include every (speed)th walkpoint of a line only.
	private int step;
	private List<WalkPoint> path;
	private Vertex vStart;
	private Vertex vEnd;
	private String edgeLabel;
	private HashMap<String, List<WalkPoint> > edgePoints = new HashMap<>();
	private Projection prEnd;
	private Projection prStart;
	private SlopeRoute slopeRoute;

	public WalkGraph(WalkGraphModel model) {
		this.model = model;
		path = new ArrayList<>();
		step = DEFAULT_STEP;
		routeBuilder = new RouteBuilder(this);
	}

	public WalkGraph(String data) {
		this(new WalkGraphModel(data));
	}

	// Graph implementation

	public WalkGraph(TextResource res) {
		this(res.getText());
	}

	public void addVertex(double x, double y, String label) {
		getModel().addVertex(x, y, label);
		debug("Vertex added: " + getModel().getVertex(label));
	}

	public void connect(String label1, String label2) {
		getModel().connect(label1, label2);
	}

	/**
	 * creates a new, temporary vertex in the graph from projection or it
	 * returns an existing one if it equals with projection.
	 * 
	 * @param pr
	 *            Projection of a screen point to an edge.
	 * @param label
	 *            The label of temporary vertex
	 * @return temporary or existing vertex located by pr.
	 */
    private Vertex createVertexIfNeeded(Projection pr, String label) {
		if (pr.e != null) {
			// new vertex in graph
			pr.v.setLabel(label);
			getModel().addVertex(pr.v);
			getModel().connect(pr.e.start, pr.v);
			getModel().connect(pr.e.end, pr.v);
		}

		return pr.v;
	}

	@Override
	public WalkPoint walkPointAt(double x, double y) {
		vProjection = getModel().getProjectionAt(x, y);
		return vProjection.v;
	}

	private void debug(String msg) {
		App.debug("[WALKGRAPH]" + msg);
	}

	private void drawEdge(Context2d ctx, Edge e, String color, double width) {
		if (e == null) {
			return;
		}
		int left = App.getCurrentScreen().getLeft();
		ctx.save();
		ctx.setStrokeStyle(color);
		ctx.setLineWidth(width);

		ctx.beginPath();
		ctx.moveTo(left + e.start.getX(), e.start.getY());
		ctx.lineTo(left + e.end.getX(), e.end.getY());
		ctx.stroke();
		ctx.closePath();
		ctx.restore();
	}

	private void drawPoint(Context2d ctx, Vertex v, String color, int size) {
		if (v == null) {
			return;
		}
		int left = App.getCurrentScreen().getLeft();

		ctx.save();
		ctx.setFillStyle(color);
		ctx.fillRect(left + v.getX() - size, v.getY() - size, size, size);
		ctx.setFillStyle("blue");
		ctx.setFont("8pt helvetica bold");
		ctx.fillText(v.getLabel(), left + v.getX(), v.getY());
		ctx.restore();
	}

	public Vertex getVertex(String label) {
		return getModel().getVertex(label);
	}

	public WalkGraphModel getModel() {
		return model;
	}

	public void setModel(WalkGraphModel model) {
		this.model = model;
	}

	// WalkAlgorithm interface

	@Override
	public void reset() {
		slopeRoute = null;
		vStart = null;
		vEnd = null;
	}

    @Override
	public RouteInterface getRoute(double x0, double y0, double x1, double y1) {
		if (App.has(Feature.SLOPE_ROUTE)) {
			return null;
		}
    	Projection prStart = getModel().getProjectionAt(x0, y0);
		Projection prEnd = getModel().getProjectionAt(x1, y1);
		App.timeElapsed("calculating projections");
		path.clear();

		if (prStart.e != null && prStart.e == prEnd.e) {
			// start and end are in the same edge
			path = routeBuilder.build(prStart.v, prEnd.v);
		} else {
			// Compute the route by Dijsktra's algorithm
			vStart = createVertexIfNeeded(prStart, LABEL_START);
			vEnd = createVertexIfNeeded(prEnd, LABEL_END);
			List<Vertex> vPoints = getModel().dijsktra(vStart, vEnd);

			for (int i = 0; i < vPoints.size() - 1; i++) {
				Vertex p0 = vPoints.get(i);
				Vertex p1 = vPoints.get(i + 1);

				String lbStart = p0.getLabel();
				if ("".equals(lbStart)) {
					lbStart = p1.getNeighbour(0).getLabel();
				}

				String lbEnd = p1.getLabel();
				if ("".equals(lbEnd)) {
					lbEnd = p1.getNeighbour(1).getLabel();
					if (lbEnd.equals(lbStart)) {
						lbEnd = p1.getNeighbour(0).getLabel();
					}
				}
				edgeLabel = p0.getLabel() + " - " + lbEnd;
				if (edgePoints.containsKey(edgeLabel)) {
					path.addAll(edgePoints.get(edgeLabel));
				} else {
					List<WalkPoint> list = routeBuilder.build(p0, p1);
					edgePoints.put(edgeLabel, list);
					path.addAll(list);
				}
			}

			getModel().removeVertex(LABEL_START);
			getModel().removeVertex(LABEL_END);
		}
		return new Route(path, step);
	}

	public void initRoute(double x0, double y0, double x1, double y1, int step) {
		prStart = getModel().getProjectionAt(x0, y0);
		prEnd = getModel().getProjectionAt(x1, y1);
		if (slopeRoute == null) {
			slopeRoute = new SlopeRoute(step);
		} else {
			slopeRoute.clear();
		}
		if (prStart.e != null && prEnd.e != null && prStart.e == prEnd.e) {

			App.debug("same edge " + prStart.v + " - " + prEnd.v);
			// start and end are in the same edge
			slopeRoute.add(prStart.v, prEnd.v, "");
		}
	}

	private boolean createEndPoints() {
		App.markTime();
		if (vStart != null) {
    		return false;
		}
		vStart = createVertexIfNeeded(prStart, LABEL_START);
		vEnd = createVertexIfNeeded(prEnd, LABEL_END);

		return true;
	}

	public void buildRoute() {
		if (slopeRoute.isReady()) {
			return;
		}

		if (slopeRoute.isSingle()) {
    		slopeRoute.setReady(true);
			return;
		}

		if (createEndPoints()) {
			return;
		}

		// Compute the route by Dijsktra's algorithm
		App.markTime();
		List<Vertex> vPoints = getModel().dijsktra(vStart, vEnd);

		for (int i = 0; i < vPoints.size() - 1; i++) {
			Vertex p0 = vPoints.get(i);
			Vertex p1 = vPoints.get(i + 1);

			String lbStart = p0.getLabel();
			if ("".equals(lbStart)) {
				lbStart = p1.getNeighbour(0).getLabel();
			}

			String lbEnd = p1.getLabel();
			if ("".equals(lbEnd)) {
				lbEnd = p1.getNeighbour(1).getLabel();
				if (lbEnd.equals(lbStart)) {
					lbEnd = p1.getNeighbour(0).getLabel();
				}
			}
			edgeLabel = p0.getLabel() + " - " + lbEnd;

			slopeRoute.add(p0.duplicate(), p1.duplicate(), edgeLabel);
		}

		getModel().removeVertex(LABEL_START);
		getModel().removeVertex(LABEL_END);
		slopeRoute.setReady(true);
		App.timeElapsed("[ROUTE] build");


	}

	public RouteInterface getRoute() {
    	return slopeRoute;
	}

	@Override
	public boolean isRouteReady() {
		return slopeRoute != null && slopeRoute.isReady();
	}

	@Override
	public boolean isValid() {
		return getModel().isValid();
	}

	@Override
	public void setScaleRange(double min, double max) {
		getModel().setScaleRange(min, max);
	}

	@Override
	public double getScale(double y) {
		return getModel().getScale(y);
	}

	@Override
	public int getStep() {
		return slopeRoute.step;
	}

	@Override
	public void visualize(Layer layer) {
		layer.clear();

		int diffLeft = layer.getLeft();
		Context2d ctx = layer.getContext2d();
		for (Edge e : getModel().getEdges()) {
			drawEdge(ctx, e, "green", EDGE_WIDTH);
		}

		if (vProjection != null) {
			drawEdge(ctx, vProjection.e, "yellow", EDGE_WIDTH * 2);
			drawPoint(ctx, vProjection.v, "red", PROJECTION_WIDTH * 2);

		}

		int left = App.getCurrentScreen().getLeft();

		for (Vertex p : getModel().getVertices()) {
			drawPoint(ctx, p, "blue", VERTEX_SIZE);

		}
		if (vStart != null) {
			drawPoint(ctx, vStart, "white", VERTEX_SIZE);

		}

		if (vEnd != null) {
			drawPoint(ctx, vEnd, "white", VERTEX_SIZE);

		}

	}

	// BuildRoute interface
	@Override
	public void appendRoute(List<WalkPoint> points, int x, int y) {
		WalkPoint p = new WalkPoint(x, y, getScale(y));
		p.setLabel(edgeLabel);
		// App.debug("[LABEL]" + p.getLabel());
		points.add(p);
	}

	@Override
	public void doDestroy() {
	    App.infoDestroy("walkGraph");
		if (model != null) {
		    model.destroy();
        }
		reset();
		edgePoints.clear();
		edgePoints = null;
		vProjection = null;
		vStart = null;
		vEnd = null;
        prStart = null;
        prEnd = null;
        model = null;
	 }
}
