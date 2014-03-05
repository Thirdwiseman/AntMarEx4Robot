package util;


import java.util.ArrayList;
import java.util.Iterator;


public class StackAgenda<E> implements interfaces.Agenda<E> {

	private ArrayList<E> stack;

	public StackAgenda() {
		stack = new ArrayList<E>();
	}

	@Override
	public E pop() {
		if(!stack.isEmpty()){
		E pop = stack.get(0);
		stack.remove(0);
		return pop;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean isEmpty() {

		return stack.size() == 0;
	}

	@Override
	public void push(E _item) {
		stack.add(0,_item);
	}

	@Override
	public boolean contains(E _item) {
		return stack.contains(_item);
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		return stack.size();
	}

}
