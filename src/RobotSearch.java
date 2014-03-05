import java.util.ArrayList;

import util.Graph;
import Robot.RobotMove;
import Robot.RobotPuzzle;
import util.Search;

public class RobotSearch {
	public static void main(String[] args) {

		//Graph g = new Graph(5, 5);
		Graph g = new Graph(10, 8);

		g.createBlockage(0, 1, 1, 1);
		g.createBlockage(0, 2, 0, 3);
		g.createBlockage(1, 0, 2, 0);
		g.createBlockage(5, 0, 6, 0);
		g.createBlockage(4, 1, 5, 1);
		g.createBlockage(4, 2, 5, 2);
		g.createBlockage(5, 2, 5, 3);
		g.createBlockage(2, 2, 2, 3);
		g.createBlockage(2, 3, 3, 3);
		g.createBlockage(1, 5, 2, 5);
		g.createBlockage(3, 6, 4, 6);
		g.createBlockage(4, 4, 5, 4);
		g.createBlockage(5, 5, 5, 6);
		g.createBlockage(6, 4, 7, 4);
		g.createBlockage(6, 5, 7, 5);

		System.out.println(g);
		
		Search<RobotPuzzle, RobotMove> search = new Search<RobotPuzzle, RobotMove>(2, new RobotPuzzle(
				g, 0, 0, 9, 7), new RobotPuzzle(g, 9, 7, 0, 0));
		
		System.out.println(search.findSolution());
		
		System.exit(0);
		System.out.println(g.toString());
		
		
		System.exit(0);
		
		
		RobotPuzzle rPuzzle = new RobotPuzzle(g, 0, 0, 4, 4);
		RobotPuzzle rGoal = new RobotPuzzle(g, 4, 4, 4, 4);

		ArrayList<RobotMove> path = new ArrayList<RobotMove>();

		System.out.println("Depth-First Search");
		Search<RobotPuzzle, RobotMove> RobotSearchD = new Search<RobotPuzzle, RobotMove>(
				1, new RobotPuzzle(rPuzzle), rGoal);
		path = RobotSearchD.findSolution();
		System.out.println();
		System.out.println(path);
		System.out.println();
		System.out.println("Breadth-First Search");
		Search<RobotPuzzle, RobotMove> RobotSearchB = new Search<RobotPuzzle, RobotMove>(
				0, new RobotPuzzle(rPuzzle), rGoal);
		path = RobotSearchB.findSolution();
		System.out.println();
		System.out.println(path);
		System.out.println();
		System.out.println("A* Search");
		Search<RobotPuzzle, RobotMove> RobotSearchA = new Search<RobotPuzzle, RobotMove>(
				2, new RobotPuzzle(rPuzzle), rGoal);
		path = RobotSearchA.findSolution();
		System.out.println();
		System.out.println(path);
	}
}