
package util;

import interfaces.Puzzle;

public class SearchNode<T extends Puzzle, A> implements Comparable<SearchNode<T, A>> {

	private final A action;
	private final T state;
	private final SearchNode nodePrev;
	private final int depth;

	public SearchNode(SearchNode nodePrev, A action, T state) {
		this.action = action;
		this.state = state;
		this.nodePrev = nodePrev;
		if(nodePrev == null){
			this.depth = 0;
		}
		else {
			this.depth = nodePrev.getDepth() + 1;
		}
		
	}

	public A getAction() {
		return this.action;
	}

	public T getState() {
		return this.state;
	}
	
	public SearchNode getPrevNode() {
		return this.nodePrev;
	}


	@Override 
	public boolean equals(Object _that) {
		if(_that instanceof SearchNode) {
			SearchNode<?, ?> that = (SearchNode<?, ?>) _that;
			return this.getState().equals(that.getState());
		}
		else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(state);
		if (action != null) {
			sb.append("\n -> \n");
			sb.append(action);
		}

		return sb.toString();
	}

	public int getDepth() {
		return depth;
	}
	
	private int f(){
		int f;
		f = state.h() + depth;
		return f;
	}

	@Override
	public int compareTo(SearchNode<T, A> that) {
		return this.f() - that.f();
	}



}