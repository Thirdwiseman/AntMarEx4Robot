package util;

public class Graph {
	private GraphNode[] graph;
	private GraphNode current;
	private final int width;
	private final int height;

	public Graph(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.graph = new GraphNode[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				graph[x + y * width] = new GraphNode(x, y);
			}
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x > 0) {
					graph[x + y * width].setWest(graph[(x - 1) + y * width]);
				}
				if (y > 0) {
					graph[x + y * width].setNorth(graph[x + (y - 1) * width]);
				}
				if (x < width - 1) {
					graph[x + y * width].setEast(graph[(x + 1) + y * width]);
				}
				if (y < height - 1) {
					graph[x + y * width].setSouth(graph[x + (y + 1) * width]);
				}
			}
		}
		current = graph[0];
	}

	public void moveNorth() {
		if (current.getNorth() != null) {
			current = current.getNorth();
		}
	}

	public void moveEast() {
		if (current.getEast() != null) {
			current = current.getEast();
		}
	}

	public void moveSouth() {
		if (current.getSouth() != null) {
			current = current.getSouth();
		}
	}

	public void moveWest() {
		if (current.getWest() != null) {
			current = current.getWest();
		}
	}

	public GraphNode getCurrent() {
		return current;
	}

	public void createBlockage(int x1, int y1, int x2, int y2) {
		// assert(x1 == x2 || y1 == y2);
		y1 = height -1 - y1;
		y2 = height -1 - y2;
		if (x1 == x2) {

			graph[x1 + Math.min(y1, y2) * width].setSouth(null);
			graph[x1 + Math.max(y1, y2) * width].setNorth(null);

		} else if (y1 == y2) {
			graph[Math.min(x1, x2) + y1 * width].setEast(null);
			graph[Math.max(x1, x2) + y1 * width].setWest(null);
		}

	}

	public GraphNode getNode(int x, int y) {
		return graph[x + (height -1 - y) * width];
	}

	public String toString() {

		String mid;
		String bot;
		String res = "#########################################\n";
		for (int y = 0; y < height; y++) {
			mid = "#";
			bot = "#";

			for (int x = 0; x < width; x++) {

				if (graph[x + y * width].getEast() == null) {
					mid += "   #";
				} else {
					mid += "    ";
				}
				if (graph[x + y * width].getSouth() == null) {
					bot += "####";
				} else {
					bot += "   #";
				}

				if (y == height - 1) {
					bot = "#########################################";
				} else {

				}

			}


			res += mid + "\n" + bot + "\n";

		}

		return res;

	}

}
