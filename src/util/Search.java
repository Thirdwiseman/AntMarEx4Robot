package util;

import java.util.ArrayList;
import java.util.List;

import interfaces.Agenda;
import interfaces.Puzzle;
import util.QueueAgenda;
import util.SearchNode;
import util.StackAgenda;

public class Search<P extends Puzzle<P, A>, A> {
	private Agenda<SearchNode<P, A>> frontier;
	private Agenda<SearchNode<P, A>> explored;
	private List<SearchNode<P, A>> successors;
	private P initial;
	private P goal;

	public Search(int searchType, P initial, P goal) {
		super();
		this.initial = initial;
		this.goal = goal;

		if (searchType == 1) {
			frontier = new StackAgenda<SearchNode<P, A>>();
		} else if(searchType == 2){
			frontier = new QueueAgenda<SearchNode<P, A>>(true);
		} else {
			frontier = new QueueAgenda<SearchNode<P, A>>(false);
		}
		explored = new QueueAgenda<SearchNode<P, A>>(false);
		successors = new ArrayList<SearchNode<P, A>>();

	}

	public ArrayList<A> findSolution() {

		System.out.println(initial);

		frontier.push(new SearchNode<P, A>(null, null, initial));

		SearchNode<P, A> current;
		boolean found = false;
		while (!found) {
			
			current = frontier.pop();
			explored.push(current);	
			if (current.getState().isGoal(goal)) {
				found = true;
				System.out.println("found");
				return path(current);
				

			} else if(current.getDepth() < 31) {
				successors.clear();
				
				successors = current.getState().getSuccessors(current);
				
				for (SearchNode<P, A> successor : successors) {
					if (!frontier.contains(successor)
							&& !explored.contains(successor)) {
						frontier.push(successor);
					}

				}
			}
		}
		return null;
	}

	public ArrayList<A> path(SearchNode<P, A> end) {
		ArrayList<A> path = new ArrayList<A>();
		System.out.println("depth: " + end.getDepth());
		while (end.getPrevNode() != null) {
			path.add(0, end.getAction());
			end = end.getPrevNode();
		}
		System.out.println("Nodes Explored in Search: " +explored.getSize());
		System.out.println();
		
		
		
		
		
		
		return path;

	}

}
