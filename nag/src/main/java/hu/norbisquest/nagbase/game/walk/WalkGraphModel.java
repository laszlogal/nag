package hu.norbisquest.nagbase.game.walk;

import hu.norbisquest.nagbase.core.Destroyable;
import hu.norbisquest.nagbase.core.NAGObject;
import hu.norbisquest.nagbase.game.App;

import java.util.*;

/*
 * vertices, edges, edgepoints
 * 
 */
public class WalkGraphModel extends NAGObject {

	private static final int ARGSC_FIXED_VERTEX = 6;
	// scaling parameters
	private double scaleMin;
	private int top;
	private int height;
	private double scaleStep;

	class Vertex extends WalkPoint implements Destroyable {
		private Map<Vertex, Edge> neighbourMap;
		private boolean fixScale;

		Vertex(double x, double y, double scale, String label) {
			super(x, y, scale);
			setLabel(label);
			neighbourMap = new HashMap<>();
		}

		void addNeighbour(Vertex v, Edge e) {
			neighbourMap.put(v, e);
		}

		void removeNeighbour(Vertex v) {
			Edge e = neighbourMap.get(v);
			edges.remove(e);
			neighbourMap.remove(v);
		}

		Set<Vertex> getNeighbours() {
			return neighbourMap.keySet();
		}

		Vertex getNeighbour(int idx) {
			return (Vertex) getNeighbours().toArray()[idx];
		}

		boolean hasNeighbour(Vertex v) {
			return neighbourMap.containsKey(v);
		}

		@Override
		public String toString() {
			return getLabel() + ": " + super.toString();
		}

		@Override
		public void setScale(double scale) {
			if (!isFixScale()) {
				super.setScale(scale);
			}
		}

		boolean isFixScale() {
			return fixScale;
		}

		void setFixScale(boolean fixScale) {
			this.fixScale = fixScale;
		}

		void setScaleAsFixed(double scaleFixed) {
			super.setScale(scaleFixed);
			setFixScale(true);
		}

		@Override
		public void destroy() {
			edges.clear();
			neighbourMap.clear();
		}

		@Override
		public boolean isDestroyed() {
			return false;
		}
	}

	class Edge {

		Vertex start;
		Vertex end;
		private int weight;

		Edge(Vertex v1, Vertex v2) {
			start = v1;
			end = v2;

			start.addNeighbour(end, this);
			end.addNeighbour(start, this);
			weight = (int) (lengthSquared() + 20 * (Math.abs(v2.getScale() - v1.getScale())));

		}

		int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		@Override
		public String toString() {
			return "Edge(" + start + ", " + end + ")";
		}

		Vertex projection(double x, double y) {
			Vertex p = null;
			double l2 = lengthSquared();
			if (l2 == 0) {
				return start;
			}

			double t = ((x - start.getX()) * (end.getX() - start.getX())
					+ (y - start.getY()) * (end.getY() - start.getY())) / l2;

			if (t < 0) {
				return start;
			}

			if (t > 1) {
				return end;
			}

			p = new Vertex(start.getX() + t * (end.getX() - start.getX()),
					start.getY() + t * (end.getY() - start.getY()), 0, "");
			return p;
		}

		double sqr(double x) {
			return x * x;
		}

		private double lengthSquared() {
			return sqr(start.getX() - end.getX()) + sqr(start.getY() - end.getY());
		}
	}

	class Projection {
		Vertex v;
		Edge e;

		Projection(Vertex v, Edge e) {
			this.v = v;
			this.e = e;
		}
	}

	private List<Vertex> vertices;
	private List<Vertex> projections;
	private List<Edge> edges;
	private Map<String, Vertex> vertexMap;
	private String data = "";

	@Deprecated
	public WalkGraphModel(int top, int bottom, double scaleMin, double scaleMax) {
		this();
		this.top = top;
		height = bottom - top;
		setScaleRange(scaleMin, scaleMax);
	}

	private void setDimension(int top, int bottom, double scaleMin, double scaleMax) {
		this.top = top;
		height = top == bottom ? 1 : bottom - top;

		setScaleRange(scaleMin, scaleMax);

		App.debug("[MODEL] top: " + top + " bottom: " + bottom + " height: " + height);
		App.debug("[MODEL] scaleMin: " + scaleMin + " scaleMax: " + scaleMax);
	}

	private WalkGraphModel() {
		vertices = new ArrayList<>();
		projections = new ArrayList<>();
		edges = new ArrayList<>();

		vertexMap = new HashMap<>();
	}

	WalkGraphModel(String data) {
		this();
		loadData(data);
		setVertexScales();
	}

	private void setVertexScales() {
		for (Vertex v : vertices) {
			v.setScale(getScale(v.getY()));
		}
	}

	private void clear() {
		vertices.clear();
		projections.clear();
		edges.clear();
		vertexMap.clear();
	}

	private void loadData(String data) {
		this.data = data;
		clear();
		double minY = Integer.MAX_VALUE;
		double maxY = 0;
		Double sMin = null;
		Double sMax = null;
		double y;

		String[] rows = data.split(";");
		for (String row : rows) {
			String[] cols = row.trim().split(" ");
			App.debug("[WALKGRAPH] cols" + Arrays.toString(cols));
			String cmd = cols[0];
			String v;
			Vertex vp;
			switch (cmd) {
			case "scale":
				sMin = Double.parseDouble(cols[1]);
				sMax = Double.parseDouble(cols[2]);
				break;
			case "v":
				App.debug("[GRAPH] v length: " + cols.length);
				App.debug("[GRAPH] cols: " + Arrays.toString(cols));

				y = Double.parseDouble(cols[2]);

				vp = addVertex(Double.parseDouble(cols[1]), y, cols[3].trim());

				if (cols.length == ARGSC_FIXED_VERTEX) {
					if ("fix".equals(cols[4].trim())) {
						double s = Double.parseDouble(cols[5]);
						App.debug("[GRAPH] " + cols[5] + " = " + s);
						vp.setScaleAsFixed(s);
					}
				}

				if (y < minY) {
					minY = y;
				}

				if (y > maxY) {
					maxY = y;
				}
				break;
			case "c":
				connect(cols[1], cols[2]);
				break;
			case "cAll":
				v = cols[1];
				for (int i = 2; i < cols.length; i++) {
					connect(v, cols[i]);
					v = cols[i];
				}
			}
		}

		if (sMin != null) {
			setDimension((int) minY, (int) maxY, sMin, sMax);
		}
	}

	Vertex addVertex(double x, double y, String label) {
		Vertex v = new Vertex(x, y, getScale(y), label);
		addVertex(v);
		return v;
	}

	void addVertex(Vertex v) {
		getVertices().add(v);
		vertexMap.put(v.getLabel(), v);
	}

	public Vertex getVertex(String label) {
		Vertex v = vertexMap.get(label);
		if (v == null) {
			App.error("No such vertex: " + label);
		}

		return v;
	}

	public void removeVertex(String label) {
		Vertex v = getVertex(label);
		if (v == null) {
			return;
		}

		for (Vertex n : v.getNeighbours()) {
			n.removeNeighbour(v);
		}
		vertices.remove(v);
	}

	boolean connect(Vertex v1, Vertex v2) {
		if (v1 == null || v2 == null) {
			return false;
		}

		edges.add(new Edge(v1, v2));
		return true;
	}

	void connect(String label1, String label2) {
		Vertex v1 = getVertex(label1);
		Vertex v2 = getVertex(label2);

		connect(v1, v2);
	}

	private boolean hasEdge(Vertex v1, Vertex v2) {
        return v1 != null && v2 != null && v1.hasNeighbour(v2) && v2.hasNeighbour(v1);

    }

	public boolean hasEdge(String label1, String label2) {
		return hasEdge(getVertex(label1), getVertex(label2));
	}

	public boolean contains(String label) {
		return contains(getVertex(label));
	}

	// Label does not matter, only coords.
    private boolean contains(Vertex v) {
		return contains(v.getX(), v.getY());
	}

	private boolean contains(double x, double y) {
		return vertexAt(x, y) != null;
	}

	private Vertex vertexAt(double x, double y) {
		for (Vertex vp : getVertices()) {
			if (vp.getX() == x && vp.getY() == y) {
				return vp;
			}
		}

		return null;
	}

	List<Vertex> getVertices() {
		return vertices;
	}

	public List<Vertex> getProjections() {
		return projections;
	}

	public boolean isValid() {
		return !vertices.isEmpty();
	}

	List<Edge> getEdges() {
		return edges;
	}

	Projection getProjectionAt(double x, double y) {
		Vertex v = vertexAt(x, y);

		if (v != null) {
			return new Projection(v, null);
		}

		projections.clear();
		double minD = Double.MAX_VALUE;
		Edge pE = null;
		for (Edge e : edges) {
			Vertex p = e.projection(x, y);
			if (p != null) {
				projections.add(p);
				double d = (x - p.getX()) * (x - p.getX()) + (y - p.getY()) * (y - p.getY());

				if (d < minD) {
					v = p;
					minD = d;
					pE = e;
				}

			}
		}
		if (v != null) {
			Vertex v2 = vertexAt(v.getX(), v.getY());
			// if projection equals an existing vertex
			if (v2 != null) {
				return new Projection(v2, null);
			}

			v.setScale(getScale(v.getY()));
		}
		return new Projection(v, pE);
	}

	private PriorityQueue<Vertex> newPriorityQueue(final Map<Vertex, Integer> dist) {
		return new PriorityQueue<>(100, (p1, p2) -> {
			Integer d1 = dist.get(p1);
			Integer d2 = dist.get(p2);
			if (d1.equals(d2)) {
				return 0;
			}

			return d1 < d2 ? -1 : 1;

		});

	}

	List<Vertex> dijsktra(String label1, String label2) {
		Vertex v1 = getVertex(label1);
		Vertex v2 = getVertex(label2);
		if (v1 != null && v2 != null) {
			return dijsktra(v1, v2);
		}

		return null;
	}

	List<Vertex> dijsktra(Vertex start, Vertex end) {

		List<Vertex> path = new ArrayList<>();

		final Map<Vertex, Integer> dist = new HashMap<>();
		final Map<Vertex, Vertex> prev = new HashMap<>();

		PriorityQueue<Vertex> unvisited = newPriorityQueue(dist);

		for (Vertex p : vertices) {
			dist.put(p, Integer.MAX_VALUE / 2);
			unvisited.add(p);
		}

		dist.put(start, 0);
		while (!unvisited.isEmpty()) {
			Vertex u = unvisited.poll();

			for (Vertex v : u.getNeighbours()) {
				Edge e = u.neighbourMap.get(v);
				// debug("neighbour " + v.label + " weight " + e.getWeight());
				int alt = dist.get(u) + e.getWeight();
				if (dist.containsKey(v)) {
					if (alt < dist.get(v)) {
						// debug("changing " + v + "'s weight to " + alt);
						dist.put(v, alt);
						prev.put(v, u);

						// re-add queue element to take priority
						// change in action
						unvisited.remove(v);
						unvisited.add(v);
					}
				}
			}
		}

		prev.put(start, null);
		Vertex u = end;
		int i = 20;
		while (prev.containsKey(u) && i != 0) {
			Vertex v = prev.get(u);
			path.add(u);
			u = v;
			i--;
		}

		Collections.reverse(path);
		return path;

	}

	void setScaleRange(double min, double max) {
		this.scaleMin = min;
		scaleStep = height == 0 ? 1 : (max - scaleMin) / height;
	}

	double getScale(double y) {
		return scaleMin + ((y - top) * scaleStep);
	}

	public String getData() {
		return data;
	}

	@Override
	public void doDestroy() {
		for (Vertex v: vertices) {
			v.destroy();
		}
		vertices.clear();
		vertices = null;
		vertexMap = null;
		projections = null;
		edges = null;
		data = null;
	}
}
