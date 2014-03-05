package interfaces;

import java.util.List;

import util.SearchNode;

public interface Puzzle<P extends Puzzle, A> {
	boolean performAction(A action);
	boolean isPossibleMove(A action);
	boolean isGoal(P goal);
	boolean equals(Object o);
	int h();
	List<SearchNode<P,A>> getSuccessors(SearchNode<P, A> s);	
}
