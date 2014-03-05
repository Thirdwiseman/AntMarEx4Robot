package util;

import java.util.Arrays;

public class GraphNode {
	private GraphNode north;
	private GraphNode east;
	private GraphNode south;
	private GraphNode west;

	private int x;
	private int y;

	public GraphNode(GraphNode north, GraphNode east, GraphNode south,
			GraphNode west, int x, int y) {
		super();
		this.setNorth(north);
		this.setEast(east);
		this.setSouth(south);
		this.setWest(west);
		this.x = x;
		this.y = y;
	}

	public GraphNode(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public GraphNode getNorth() {
		return north;
	}

	public void setNorth(GraphNode north) {
		this.north = north;
	}

	public GraphNode getEast() {
		return east;
	}

	public void setEast(GraphNode east) {
		this.east = east;
	}

	public GraphNode getSouth() {
		return south;
	}

	public void setSouth(GraphNode south) {
		this.south = south;
	}

	public GraphNode getWest() {
		return west;
	}

	public void setWest(GraphNode west) {
		this.west = west;
	}

	public String toString() {
		return "Node: [" + x + "," + y + "]";
	}

	public boolean equals(Object _that) {
		if (_that instanceof GraphNode) {
			GraphNode that = (GraphNode) _that;	
			return this.x == that.getX() && this.y == that.getY();
		}

		return false;
	}

	private int getX() {
		return this.x;
	}
	
	private int getY() {
		return this.y;
	}
	
	public int manhattan(GraphNode gn){
		return Math.abs(this.x - gn.getX()) + Math.abs(this.y - gn.getY());
	}
}
