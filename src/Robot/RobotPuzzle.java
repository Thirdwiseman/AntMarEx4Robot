package Robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import util.Graph;
import util.GraphNode;
import util.SearchNode;
import interfaces.Puzzle;

public class RobotPuzzle implements Puzzle<RobotPuzzle, RobotMove> {

	private GraphNode location;
	private GraphNode goal;
	private Graph graph;
	private final Random random = new Random();

	public RobotPuzzle(Graph g, int x, int y, int gx, int gy) {
		graph = g;
		location = g.getNode(x, y);
		goal = g.getNode(gx, gy);
		System.out.println();
	}

	public RobotPuzzle(RobotPuzzle r) {
		this.graph = r.graph;
		this.location = r.location;
		this.goal = r.goal;
	}

	@Override
	public int h() {
		return location.manhattan(goal);
	}

	public String toString() {
		return location.toString();
	}

	@Override
	public boolean performAction(RobotMove action) {
		if (isPossibleMove(action)) {
			int dir = action.getDir();
			switch (dir) {
			case 0:
				location = location.getNorth();
				break;
			case 1:
				location = location.getEast();
				break;
			case 2:
				location = location.getSouth();
				break;
			case 3:
				location = location.getWest();
				break;

			}
			return true;

		} else {
			return false;
		}

	}

	@Override
	public boolean isPossibleMove(RobotMove action) {
		int dir = action.getDir();
		switch (dir) {
		case 0:
			return location.getNorth() != null;
		case 1:
			return location.getEast() != null;
		case 2:
			return location.getSouth() != null;
		case 3:
			return location.getWest() != null;
		default:
			return false;
		}
	}

	public boolean equals(Object o){
		if (o instanceof RobotPuzzle) {
			RobotPuzzle that = (RobotPuzzle) o;

			return this.location == that.location;
		}
	
		return false;
	}
	
	
	@Override
	public boolean isGoal(RobotPuzzle goal) {
		
		return equals(goal);
	}

	@Override
	public List<SearchNode<RobotPuzzle, RobotMove>> getSuccessors(
			SearchNode<RobotPuzzle, RobotMove> s) {
		ArrayList<SearchNode<RobotPuzzle, RobotMove>> successors = new ArrayList<SearchNode<RobotPuzzle, RobotMove>>();
		
		RobotPuzzle current = s.getState();
		
		for(int i = 0; i < 4; i++){
			if(isPossibleMove(new RobotMove(i))){
				RobotPuzzle successor = new RobotPuzzle(current);
				successor.performAction(new RobotMove(i));
				successors.add(new SearchNode<RobotPuzzle, RobotMove>(s,new RobotMove(i),successor));
			}
		}
		
		
		return successors;
	}
}
